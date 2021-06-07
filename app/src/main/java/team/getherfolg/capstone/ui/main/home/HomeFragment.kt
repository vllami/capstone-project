package team.getherfolg.capstone.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.data.JobListResultResponse
import team.getherfolg.capstone.databinding.FragmentHomeBinding
import team.getherfolg.capstone.databinding.FragmentHomeBinding.inflate
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.ui.main.home.profile.ProfileActivity
import java.io.File
import java.io.InputStream
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private var listFile: List<String> = listOf()

    private var encodedPDF: String? = null

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

            with(imageView) {

                setOnClickListener {
                    startActivity(Intent(activity, ProfileActivity::class.java))
                }
            }

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
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Intent(ACTION_GET_CONTENT).also {
                        it.apply {
                            type = "application/pdf"
                            addCategory(CATEGORY_OPENABLE)
                            createChooser(this, "")
                            startActivityForResult(this, CHOOSE_PDF_FILE)
                        }
                    }
                }

            }

            btnUpload.setOnClickListener {
                val file = File(encodedPDF)

                val requestBody = RequestBody.create("mul".toMediaTypeOrNull(), file)
                val fileToUpload =
                    MultipartBody.Part.createFormData("cv", file.name, requestBody)

                SuitableClient.getService().uploadPDF(fileToUpload)
                    .enqueue(object : Callback<JobListResultResponse> {
                        override fun onResponse(
                            call: Call<JobListResultResponse>,
                            response: Response<JobListResultResponse>
                        ) {
                            if (response.isSuccessful)
                                Log.d("On Response: Success", response.toString())
                            Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<JobListResultResponse>, t: Throwable) {
                            Log.e("Upload", "On Failed : ${t.message.toString()}")
                        }

                    })
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_PDF_FILE && resultCode == RESULT_OK && data != null) {
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

            val path: Uri? = data.data

            try {
                val inputStream: InputStream = activity?.contentResolver?.openInputStream(path!!)!!
                val pdfInByte = ByteArray(inputStream.available())
                inputStream.read(pdfInByte)
                encodedPDF = Base64.encodeToString(pdfInByte, Base64.DEFAULT)

                true.pdfPick()
                homeBinding.resultUpload.text = "PDF has been selected"

                Toast.makeText(activity, "Document Selected", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun Boolean.pdfPick() {
        homeBinding.resultUpload.visibility = if (this) View.VISIBLE else View.GONE
    }

    companion object {
        private const val CHOOSE_PDF_FILE = 1_000
    }

}