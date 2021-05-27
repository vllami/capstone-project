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
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import team.getherfolg.capstone.R
import team.getherfolg.capstone.databinding.FragmentHomeBinding
import team.getherfolg.capstone.databinding.FragmentHomeBinding.inflate
import team.getherfolg.capstone.ui.authentication.AuthenticationActivity
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

            // time
            calendar = Calendar.getInstance()
            when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> homeBinding.tvGreet.text = "Good Moring"
                in 12..17 -> homeBinding.tvGreet.text = "Good Afternoon"
                in 18..20 -> homeBinding.tvGreet.text = "Good Evening"
                in 21..24 -> homeBinding.tvGreet.text = "Good Night"
            }

            // pop up menu
            val popUpMenu = PopupMenu(context, homeBinding.imageView)
            popUpMenu.inflate(R.menu.pop_up_menu)
            popUpMenu.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.update_foto -> {
                        Toast.makeText(requireContext(), "tes", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.verif -> {
                        Toast.makeText(requireContext(), "tes", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.logout -> {
                        mAuth.signOut()
                        Intent(activity, AuthenticationActivity::class.java).also {
                            startActivity(it)
                        }
                        true
                    }
                    else -> true
                }
            }

            homeBinding.imageView.setOnClickListener {
                try {
                    val popUp = PopupMenu::class.java.getDeclaredField("mPopUp")
                    popUp.isAccessible = true
                    val menu = popUp.get(popUpMenu)
                    menu.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    popUpMenu.show()
                }
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