@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.ui.pdfpreview

import android.content.Intent
import android.database.Cursor
import android.net.Uri
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
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import com.shockwave.pdfium.PdfDocument
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import team.getherfolg.capstone.databinding.ActivityPdfPreviewBinding
import java.io.File
import java.util.regex.Pattern

class PDFPreviewActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private lateinit var activityPdfPreviewBinding: ActivityPdfPreviewBinding

    private val pageNumber = 0
    private var pdfFileName: String? = null
    private val pdfView: PDFView? = null
    private var pdfPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityPdfPreviewBinding = ActivityPdfPreviewBinding.inflate(layoutInflater)
        setContentView(activityPdfPreviewBinding.root)

        activityPdfPreviewBinding.btnUpload.setOnClickListener {
            showControl(true)
        }
    }

    private fun launchFilePicker() {
        MaterialFilePicker()
            .withActivity(this)
            .withRequestCode(FILE_PICKER_REQUEST_CODE)
            .withHiddenFiles(true)
            .withFilter(Pattern.compile(".*\\.pdf$"))
            .withTitle("Select your CV")
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val path = data?.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
            val file = File(path)
            displayFromFile(file)
            if (path != null) {
                Log.d("Path: ", path)
                pdfPath = path
                Toast.makeText(this, "Picked file: $path", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayFromFile(file: File) {
        val uri: Uri = Uri.fromFile(File(file.absolutePath))
        pdfFileName = getFileName(uri)

        pdfView?.fromFile(file)
            ?.defaultPage(pageNumber)
            ?.onPageChange(this)
            ?.enableAnnotationRendering(true)
            ?.onLoad(this)
            ?.scrollHandle(DefaultScrollHandle(this))
            ?.spacing(10) // dp
            ?.onPageError(this)
            ?.load()
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null

        if (uri.scheme.equals("content")) {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

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

    override fun onPageChanged(page: Int, pageCount: Int) {
        var pageNumber = page
        title = String.format("%s %s / %s", pdfFileName, page + 1, pageCount)
    }

    override fun loadComplete(nbPages: Int) {
        val meta: PdfDocument.Meta = pdfView!!.documentMeta

        printBookmarksTree(pdfView.tableOfContents, "-")
    }

    private fun printBookmarksTree(tableOfContents: List<PdfDocument.Bookmark>, s: String) {
        for (b in tableOfContents) {

            // Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$s-")
            }
        }
    }

    override fun onPageError(page: Int, t: Throwable?) {
        TODO("Not yet implemented")
    }

    private fun uploadFile() {
        if (pdfPath == null) {
            Toast.makeText(this, "please select an CV PDF filec", Toast.LENGTH_LONG).show()
            return
        } else {
            showControl(true)

            // Map is used to multipart the file using okhttp3.RequestBody
            val map: MutableMap<String, RequestBody> = HashMap()
            val file = File(pdfPath)

            // Parsing any Media type file
            val requestBody: RequestBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            map["file\"; filename=\"" + file.name + "\""] = requestBody

//            val getResponse: SuitableService = SuitableClient.getService().create(SuitableService::class.java)
//            val call: Call<UploadResponse> = getResponse.upload("token", map)
//            call.enqueue(object : Callback<UploadResponse?> {
//                override fun onResponse(call: Call<UploadResponse?>?, response: Response<UploadResponse?>) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            showControl(false)
//                            val serverResponse: UploadResponse = response.body()!!
//                            Toast.makeText(
//                                applicationContext,
//                                serverResponse.dataUpload,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    } else {
//                        showControl(false)
//                        Toast.makeText(applicationContext, "problem image", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//
//                override fun onFailure(call: Call<UploadResponse?>?, t: Throwable) {
//                    showControl(false)
//                    Log.v("Response gotten is", t.message!!)
//                    Toast.makeText(
//                        applicationContext,
//                        "problem uploading image " + t.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
        }
    }

    private fun showControl(state: Boolean) {
        activityPdfPreviewBinding.apply {
            when {
                state -> {
                    progressBar.visibility = View.VISIBLE
                    tvUpload.visibility = View.GONE
                }
                else -> {
                    progressBar.visibility = View.GONE
                    tvUpload.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        const val FILE_PICKER_REQUEST_CODE = 1
    }

}