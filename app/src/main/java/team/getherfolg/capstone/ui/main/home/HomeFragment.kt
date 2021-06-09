package team.getherfolg.capstone.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import team.getherfolg.capstone.data.AppPermissions
import team.getherfolg.capstone.databinding.FragmentHomeBinding
import team.getherfolg.capstone.databinding.FragmentHomeBinding.inflate
import team.getherfolg.capstone.ui.pdfpreview.PDFPreviewActivity
import team.getherfolg.capstone.ui.preference.Constant
import java.io.File
import java.io.InputStream
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    private var encodedPDF = mutableListOf<File>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        homeBinding = inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.apply {

            Calendar.getInstance().also {
                tvGreet.apply {
                    when (it.get(Calendar.HOUR_OF_DAY)) {
                        in 0..11 -> text = "Good Morning!"
                        in 11..17 -> text = "Good Afternoon"
                        in 18..22 -> text = "Good Evening"
                        in 22..24 -> text = "Good Night!"
                    }
                }
            }

            false.pdfPick()

            btnChooseFile.setOnClickListener {
                startActivity(Intent(activity, PDFPreviewActivity::class.java))


//                if (ActivityCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    val fileIntent = Intent().setAction(Intent.ACTION_GET_CONTENT).setType("application/pdf")
//
//                    startActivityForResult(fileIntent, CHOOSE_PDF_FILE)
//                Intent(ACTION_GET_CONTENT).also {
//                    it.apply {
//                        type = "application/pdf"
//                        addCategory(CATEGORY_OPENABLE)
//                        createChooser(this, "")
//                        startActivityForResult(this, CHOOSE_PDF_FILE)
//                    }
//                }
//
//                    Log.i("Pick PDF", "Test")
//                } else {
//                    requirePermission()
//                }


            }

            btnUpload.setOnClickListener {
                if (AppPermissions.hasPermissions(
                        requireContext(),
                        *AppPermissions.STORAGE_PERMISSIONS
                    )
                ) {
                    Intent(ACTION_GET_CONTENT).also {
                        it.apply {
                            type = "application/pdf"
                            addCategory(CATEGORY_OPENABLE)
                            createChooser(this, "")
                            startActivityForResult(this, CHOOSE_PDF_FILE)
                        }
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                            AppPermissions.GALLERY_PERMISSIONS,
                            AppPermissions.PERMISSIONS_REQUEST_GALLERY
                        )
                    }
                }

//                val file = File(encodedPDF)
//                val requestBody = RequestBody.create("/".toMediaTypeOrNull(), file)
//                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
//                body.addFormDataPart("dataupload", file.name, requestBody)
//                SuitableClient.getService().uploadPDF(body.build())
//                    .enqueue(object : Callback<JobListResultResponse> {
//                        override fun onResponse(
//                            call: Call<JobListResultResponse>,
//                            response: Response<JobListResultResponse>
//                        ) {
//                            if (response.isSuccessful)
//                                Log.d("On Response: Success", response.toString())
//                            Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show()
//                        }
//
//                        override fun onFailure(call: Call<JobListResultResponse>, t: Throwable) {
//                            Log.e("Upload", "On Failed : ${t.message.toString()}")
//                        }
//
//                    })
            }
        }

    }

    private fun requirePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), Constant.PERMISSION_REQUEST_FILE
            )
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), Constant.PERMISSION_REQUEST_FILE
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_PDF_FILE && resultCode == RESULT_OK && data != null) {
            val path: Uri? = data.data

            if (resultCode == RESULT_OK) {
                if (requestCode == CHOOSE_PDF_FILE) {
                    try {
                        val inputStream: InputStream = activity?.contentResolver?.openInputStream(path!!)!!
                        val pdfInByte = ByteArray(inputStream.available())
                        inputStream.read(pdfInByte)
                        true.pdfPick()
                        homeBinding.resultUpload.text = "PDF has been selected"
                        Toast.makeText(activity, "Document Selected", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(activity, "Select PDF", Toast.LENGTH_SHORT).show()
                }
            }

//            if (ActivityCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                val uri = data.data
////            val uriString = uri.toString()
//
//                val path: String? = FilePathFormUri.get(uri!!, requireActivity().contentResolver!!)
//
//                Log.d("onActivityResult: ", path!!)
//
//                val pdfName = "CV"
//
//                UploadAttachmentToServer(path, pdfName, requireContext()).uploadAttachment()
//            }
        }
    }

    private fun Boolean.pdfPick() {
        homeBinding.resultUpload.visibility = if (this) View.VISIBLE else View.GONE
    }

    companion object {
        private const val CHOOSE_PDF_FILE = 1_000
        private const val RESULT_LOAD_FILE = 2
    }

}