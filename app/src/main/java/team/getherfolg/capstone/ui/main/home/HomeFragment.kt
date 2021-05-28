@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.ui.main.home

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.*
import com.karumi.dexter.Dexter.withContext as dexterContext

class HomeFragment : Fragment() {

    companion object {
        private const val CHOOSE_PDF_FILE = 1_000
    }

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var calendar: Calendar

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
                        startActivityForResult(createChooser(this, ""), CHOOSE_PDF_FILE)
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

            mAuth = FirebaseAuth.getInstance()
            val photo = mAuth.currentUser

            if (photo?.photoUrl != null) {
                Glide.with(this).load(photo.photoUrl).into(homeBinding.imageView)
            } else {
                Glide.with(this).load(R.drawable.ic_profile).into(homeBinding.imageView)
            }

            // time
            calendar = Calendar.getInstance()
            when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> homeBinding.tvGreet.text = "Good Morning"
                in 12..17 -> homeBinding.tvGreet.text = "Good Afternoon"
                in 18..20 -> homeBinding.tvGreet.text = "Good Evening"
                in 21..24 -> homeBinding.tvGreet.text = "Good Night"
            }

            homeBinding.imageView.setOnClickListener {
                startActivity(Intent(activity, ProfileActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_PDF_FILE && resultCode == RESULT_OK && data != null) {
            Intent(context, PDFPreviewActivity::class.java).also {
                it.apply {
                    putExtra("ViewType", "storage")
                    putExtra("FileUri", data.data.toString())
                    startActivity(this)
                }
            }
        }
    }
}