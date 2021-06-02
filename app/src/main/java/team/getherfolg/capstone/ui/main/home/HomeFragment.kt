@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.ui.main.home

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import team.getherfolg.capstone.databinding.FragmentHomeBinding
import team.getherfolg.capstone.databinding.FragmentHomeBinding.inflate
import team.getherfolg.capstone.ui.adapter.JobListAdapter
import team.getherfolg.capstone.ui.main.home.profile.ProfileActivity
import team.getherfolg.capstone.ui.viewModel.MainViewModel
import java.util.*
import com.karumi.dexter.Dexter.withContext as dexterContext

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var adapter: JobListAdapter
    private lateinit var viewModel: MainViewModel

    private var encodedPDF: String? = null
    private var jobID: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        homeBinding = inflate(layoutInflater, container, false)

        dexterContext(context)
            .withPermissions(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
            .withListener(object : BaseMultiplePermissionsListener() {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    super.onPermissionsChecked(report)
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    super.onPermissionRationaleShouldBeShown(permissions, token)
                }
            })

        homeBinding.apply {
            btnChooseFile.setOnClickListener {
                Intent(ACTION_GET_CONTENT).also {
                    it.apply {
                        type = "application/pdf"
                        addCategory(CATEGORY_OPENABLE)
                        createChooser(this, "")
                        startActivityForResult(this, CHOOSE_PDF_FILE)
                    }
                }
            }
            return root
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

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
                            in 11..18 -> text = "Good Afternoon"
                            in 19..22 -> text = "Good Evening"
                            in 22..24 -> text = "Good Night!"
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == CHOOSE_PDF_FILE && resultCode == RESULT_OK && data != null) {
//            val path: Uri? = data.data
//
//            try {
//                val inputStream: InputStream? = path?.let { context?.contentResolver?.openInputStream(it) }
//                val pdfInBytes = inputStream?.available()?.let { ByteArray(it) }
//                inputStream?.read(pdfInBytes)
//                encodedPDF = Base64.encodeToString(pdfInBytes, DEFAULT)
//
//                Toast.makeText(context, "Document Selected", Toast.LENGTH_SHORT).show()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//            Intent(context, PDFPreviewActivity::class.java).also {
//                it.apply {
//                    putExtra("ViewType", "storage")
//                    putExtra("FileUri", data.data.toString())
//                    startActivity(this)
//                }
//            }
//
//            homeBinding.btnUpload.setOnClickListener {
//                val inputStream: InputStream? =
//                    path?.let { context?.contentResolver?.openInputStream(it) }
//                val pdfInBytes = inputStream?.available()?.let { ByteArray(it) }
//                inputStream?.read(pdfInBytes)
//                encodedPDF = Base64.encodeToString(pdfInBytes, DEFAULT)
//
//                SuitableClient.getService().sendFile(encodedPDF.toString())
//                    .enqueue(object : Callback<UploadResponse> {
//                        override fun onResponse(
//                            call: Call<UploadResponse>,
//                            response: Response<UploadResponse>
//                        ) {
//                            Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show()
//                        }
//
//                        override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
//                            Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
//                        }
//
//                    })
//            }
//        }
    }

    companion object {
        private const val CHOOSE_PDF_FILE = 1_000
    }

}