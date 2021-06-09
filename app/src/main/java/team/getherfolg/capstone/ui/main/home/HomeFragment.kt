package team.getherfolg.capstone.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.os.Bundle
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
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.FilePathFormUri
import team.getherfolg.capstone.data.response.UploadResponse
import team.getherfolg.capstone.databinding.FragmentHomeBinding
import team.getherfolg.capstone.databinding.FragmentHomeBinding.inflate
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.ui.preference.Constant
import java.io.File
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private var listFile: List<String> = listOf()

    private var encodedPDF: String? = null
    private var pdfPath: String? = null

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
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
//                    val fileIntent = Intent().setAction(Intent.ACTION_GET_CONTENT).setType("application/pdf")
//
//                    startActivityForResult(fileIntent, CHOOSE_PDF_FILE)
                    Intent(ACTION_GET_CONTENT).also {
                        it.apply {
                            type = "application/pdf"
                            addCategory(CATEGORY_OPENABLE)
                            createChooser(this, "")
                            startActivityForResult(this, CHOOSE_PDF_FILE)
                        }
                    }

                    Log.i("Pick PDF", "Test")
                } else {
                    requirePermission()
                }


            }

            btnUpload.setOnClickListener {
                val file = File(pdfPath)

                val requestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                requestBody.addFormDataPart("dataupload", "", file.asRequestBody("application/pdf".toMediaTypeOrNull()))

                SuitableClient.getService().upload(requestBody.build())
                    .enqueue(object : Callback<UploadResponse> {
                        override fun onResponse(
                            call: Call<UploadResponse>,
                            response: Response<UploadResponse>
                        ) {
                            if (response.isSuccessful)
                                Log.d("On Response: Success", response.toString())
                            Toast.makeText(activity, "Upload Success", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(
                            call: Call<UploadResponse>,
                            t: Throwable
                        ) {
                            Log.e("Upload", "On Failed : ${t.message.toString()}")
                        }

                    })
            }
        }

    }

    private fun requirePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_PDF_FILE && resultCode == RESULT_OK) {

            val path: String = data.toString()
            val file = File(path)
            pdfPath = file.toString()

            Log.d("Path: ", path)
            Toast.makeText(activity, "Picked file: $path", Toast.LENGTH_LONG).show()
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