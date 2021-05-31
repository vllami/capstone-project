@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.ui.main.home

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Base64.DEFAULT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import team.getherfolg.capstone.R
import team.getherfolg.capstone.databinding.FragmentHomeBinding
import team.getherfolg.capstone.databinding.FragmentHomeBinding.inflate
import team.getherfolg.capstone.ui.pdfpreview.PDFPreviewActivity
import java.io.IOException
import java.io.InputStream
import java.util.*
import com.karumi.dexter.Dexter.withContext as dexterContext


class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    private var encodedPDF: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        homeBinding = inflate(layoutInflater, container, false)

        dexterContext(context)
            .withPermissions(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
            .withListener(object : BaseMultiplePermissionsListener() {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    super.onPermissionsChecked(report)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
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
            homeBinding.apply {
                with(imageView) {
                    FirebaseAuth.getInstance().currentUser.also {
                        when {
                            it?.photoUrl != null -> Glide
                                .with(this@HomeFragment)
                                .load(it.photoUrl)
                                .into(this)
                            else -> Glide
                                .with(this@HomeFragment)
                                .load(R.drawable.ic_profile)
                                .into(this)
                        }
                    }

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

        if (requestCode == CHOOSE_PDF_FILE && resultCode == RESULT_OK && data != null) {
            val path: Uri? = data.data

            try {
                val inputStream: InputStream? = path?.let { context?.contentResolver?.openInputStream(it) }
                val pdfInBytes = inputStream?.available()?.let { ByteArray(it) }
                inputStream?.read(pdfInBytes)
                encodedPDF = Base64.encodeToString(pdfInBytes, DEFAULT)

                Toast.makeText(context, "Document Selected", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            Intent(context, PDFPreviewActivity::class.java).also {
                it.apply {
                    putExtra("ViewType", "storage")
                    putExtra("FileUri", data.data.toString())
                    startActivity(this)
                }
            }
        }
    }

    companion object {
        private const val CHOOSE_PDF_FILE = 1_000
    }

}