package team.getherfolg.capstone.ui.pdfpreview

import android.graphics.Color.RED
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import team.getherfolg.capstone.databinding.ActivityPdfpreviewBinding
import team.getherfolg.capstone.databinding.ActivityPdfpreviewBinding.inflate

class PDFPreviewActivity : AppCompatActivity() {

    private lateinit var pdfpreviewBinding: ActivityPdfpreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pdfpreviewBinding = inflate(layoutInflater)
        setContentView(pdfpreviewBinding.root)

        if (intent != null) {
            val viewType = intent.getStringExtra("ViewType")

            if (!TextUtils.isEmpty(viewType) || viewType != null) {
                if (viewType.equals("storage")) {
                    val selectedPDF = Uri.parse(intent.getStringExtra("FileUri"))

                    pdfpreviewBinding.pdfView.apply {
                        fromUri(selectedPDF)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onDraw { canvas, pageWidth, pageHeight, displayedPage ->

                        }.onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->

                        }
                        .onPageChange { page, pageCount ->

                        }.onPageError { page, throwable ->
                            Toast.makeText(this@PDFPreviewActivity, "Error while opening page $page", Toast.LENGTH_SHORT).show()
                            Log.d("ERROR", "" + throwable.localizedMessage)
                        }
                        .onTap {
                            false
                        }
                        .onRender { nbPages, pageWidth, pageHeight ->
                            fitToWidth()
                        }
                        .enableAnnotationRendering(true)
                        .invalidPageColor(RED)
                        .load()
                    }
                }
            }
        }
    }
}