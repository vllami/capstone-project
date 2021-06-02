package team.getherfolg.capstone.ui.pdfpreview

import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
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
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.R
import team.getherfolg.capstone.databinding.ActivityPdfpreviewBinding
import team.getherfolg.capstone.databinding.ActivityPdfpreviewBinding.inflate
import java.io.File
import java.util.regex.Pattern

class PDFPreviewActivity : AppCompatActivity() {
//
//    private lateinit var pdfpreviewBinding: ActivityPdfpreviewBinding
//
//    private var pageNumber = 0
//    private var pdfFileName: String? = null
//    private val pdfView: PDFView? = null
//    private var pDialog: ProgressDialog? = null
//    private var pdfPath: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        pdfpreviewBinding = inflate(layoutInflater)
//        setContentView(pdfpreviewBinding.root)
//
//        pdfpreviewBinding.toolbar.setNavigationOnClickListener {
//            onBackPressed()
//        }
//
//        initDialog()
//    }
//
//    private fun launchPicker() {
//        MaterialFilePicker()
//            .withActivity(this)
//            .withRequestCode(FILE_PICKER_REQUEST_CODE)
//            .withHiddenFiles(true)
//            .withFilter(Pattern.compile(".*\\.pdf$"))
//            .withTitle("Select PDF file")
//            .start()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
//            val path = data?.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
//            val file = File(path)
//            displayFromFile(file)
//            if (path != null) {
//                Log.d("Path: ", path)
//                pdfPath = path
//                Toast.makeText(this, "Picked file: $path", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    private fun displayFromFile(file: File) {
//        val uri: Uri = Uri.fromFile(File(file.absolutePath))
//        pdfFileName = getFileName(uri)
//        pdfView!!.fromFile(file)
//            .defaultPage(pageNumber)
//            .onPageChange(this)
//            .enableAnnotationRendering(true)
//            .onLoad(this)
//            .scrollHandle(DefaultScrollHandle(this))
//            .spacing(10) // in dp
//            .onPageError(this)
//            .load()
//    }
//
//    private fun getFileName(uri: Uri): String? {
//        var result: String? = null
//        if (uri.scheme.equals("content")) {
//            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
//            cursor.use {
//                if (it != null && it.moveToFirst()) {
//                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                }
//            }
//        }
//        if (result == null) {
//            result = uri.lastPathSegment
//        }
//        return result
//    }
//
//
//    override fun loadComplete(nbPages: Int) {
//        val meta: PdfDocument.Meta = pdfView!!.documentMeta
//        printBookmarksTree(pdfView.tableOfContents, "-")
//    }
//
//    private fun printBookmarksTree(tree: List<PdfDocument.Bookmark>, sep: String) {
//        for (b in tree) {
//
//            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
//            if (b.hasChildren()) {
//                printBookmarksTree(b.children, "$sep-")
//            }
//        }
//    }
//
//    override fun onPageChanged(page: Int, pageCount: Int) {
//        pageNumber = page
//        title = String.format("%s %s / %s", pdfFileName, page + 1, pageCount)
//    }
//
//    override fun onPageError(page: Int, t: Throwable?) {}
//
//    private fun uploadFile() {
//        if (pdfPath == null) {
//            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show()
//            return
//        } else {
//            showpDialog()
//
//            // Map is used to multipart the file using okhttp3.RequestBody
//            val map: MutableMap<String, RequestBody> = HashMap()
//            val file = File(pdfPath)
//            // Parsing any Media type file
//            val requestBody: RequestBody = create(MediaType.parse("application/pdf"), file)
//            map["file\"; filename=\"" + file.name.toString() + "\""] = requestBody
//            val getResponse: ApiConfig = AppConfig.getRetrofit().create(ApiConfig::class.java)
//            val call: Call<ServerResponse> = getResponse.upload("token", map)
//            call.enqueue(object : Callback<ServerResponse?> {
//                override fun onResponse(call: Call<ServerResponse?>?, response: Response<ServerResponse?>) {
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            hidepDialog()
//                            val serverResponse: ServerResponse = response.body()
//                            Toast.makeText(
//                                applicationContext,
//                                serverResponse.getMessage(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    } else {
//                        hidepDialog()
//                        Toast.makeText(applicationContext, "problem image", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//
//                override fun onFailure(call: Call<ServerResponse?>?, t: Throwable) {
//                    hidepDialog()
//                    t.message?.let { Log.v("Response gotten is", it) }
//                    Toast.makeText(
//                        applicationContext,
//                        "problem uploading image " + t.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//        }
//    }
//
//    private fun initDialog() {
//        pDialog = ProgressDialog(this)
//        pDialog!!.setMessage(getString(R.string.msg_loading))
//        pDialog!!.setCancelable(true)
//    }
//
//
//    private fun showpDialog() {
//        if (!pDialog!!.isShowing) pDialog!!.show()
//    }
//
//    private fun hidepDialog() {
//        if (pDialog!!.isShowing) pDialog!!.dismiss()
//    }
//
//    companion object {
//        private const val FILE_PICKER_REQUEST_CODE = 1
//    }
}