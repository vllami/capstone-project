@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.ui.pdfpreview

import android.content.Intent
import android.content.Intent.*
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.shockwave.pdfium.PdfDocument
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.AppPermissions
import team.getherfolg.capstone.data.response.UploadResponse
import team.getherfolg.capstone.databinding.ActivityPdfPreviewBinding
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.networking.SuitableService
import java.io.File
import java.util.*

class PDFPreviewActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private lateinit var activityPdfPreviewBinding: ActivityPdfPreviewBinding

    private var pageNumber = 0
    private var pdfFileName: String? = null
    private var pdfView: PDFView? = null
    private var pdfPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityPdfPreviewBinding = ActivityPdfPreviewBinding.inflate(layoutInflater)
        activityPdfPreviewBinding.apply {
            setContentView(root)

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            btnAdd.setOnClickListener {
                if (AppPermissions.hasPermissions(this@PDFPreviewActivity, *AppPermissions.STORAGE_PERMISSIONS)) {
                    Intent(ACTION_GET_CONTENT).also {
                        it.apply {
                            type = "application/pdf"
                            addCategory(CATEGORY_OPENABLE)
                            createChooser(this, "")
                            startActivityForResult(this, FILE_PICKER_REQUEST_CODE)
                        }
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                            AppPermissions.STORAGE_PERMISSIONS,
                            AppPermissions.PERMISSIONS_REQUEST_STORAGE
                        )
                    }
                }
            }

            btnUpload.setOnClickListener {
                showControl(true)

                if (pdfPath == null) {
                    Toast.makeText(this@PDFPreviewActivity, "Please choose your CV", Toast.LENGTH_LONG).show()

                    return@setOnClickListener
                } else {
                    showControl(true)

                    val file = File(pdfPath!!)
                    val requestBody: RequestBody = RequestBody.create("application/pdf".toMediaTypeOrNull(), file)
                    val map: MutableMap<String, RequestBody> = HashMap<String, RequestBody>()
                    val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    val getResponse: SuitableService = SuitableClient.getService()
                    val call: Call<UploadResponse> = getResponse.upload(body.build())

                    map["file\"; filename=\"" + file.name + "\""] = requestBody
                    call.enqueue(object : Callback<UploadResponse?> {
                        override fun onResponse(call: Call<UploadResponse?>?, response: Response<UploadResponse?>) {
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    showControl(false)
                                    val serverResponse: UploadResponse = response.body()!!
                                    Toast.makeText(
                                        applicationContext,
                                        serverResponse.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                showControl(false)
                                Toast.makeText(applicationContext, "problem image", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<UploadResponse?>?, t: Throwable) {
                            showControl(false)
                            Log.v("Response gotten is", t.message!!)
                            Toast.makeText(
                                applicationContext,
                                "problem uploading image " + t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page
        title = String.format("%s %s / %s", pdfFileName, page + 1, pageCount)
    }

    override fun loadComplete(nbPages: Int) {
        pdfView!!.documentMeta

        printBookmarksTree(pdfView!!.tableOfContents, "-")
    }

    override fun onPageError(page: Int, t: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val path: String = data.data.toString()
            val file = File(path)

            displayFromFile(file)

            Log.d("Path: ", path)
            pdfPath = path
            Toast.makeText(this, "Picked file: $path", Toast.LENGTH_LONG).show()
        }
    }

    private fun printBookmarksTree(tableOfContents: List<PdfDocument.Bookmark>, sep: String) {
        for (bookmark in tableOfContents) {
            Log.e("printBookmarksTree", String.format("%s %s, p %d", sep, bookmark.title, bookmark.pageIdx))

            if (bookmark.hasChildren()) {
                printBookmarksTree(bookmark.children, "$sep-")
            }
        }
    }

    private fun displayFromFile(file: File) {
        val uri = Uri.fromFile(File(file.absolutePath))
        pdfFileName = getFileName(uri)

        pdfView!!.fromFile(file)
            .defaultPage(pageNumber)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10)
            .onPageError(this)
            .load()
    }

    private fun getFileName(uri: Uri?): String? {
        var result: String? = null
        if (uri!!.scheme.equals("content")) {
            val cursor: Cursor? = contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )
            cursor.use {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.lastPathSegment
        }
        return result
    }

    private fun showControl(state: Boolean) {
        activityPdfPreviewBinding.apply {
            when {
                state -> {
                    progressBar.visibility = View.VISIBLE
                    imgUpload.visibility = View.GONE
                }
                else -> {
                    progressBar.visibility = View.GONE
                    imgUpload.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        const val FILE_PICKER_REQUEST_CODE = 1
    }

}