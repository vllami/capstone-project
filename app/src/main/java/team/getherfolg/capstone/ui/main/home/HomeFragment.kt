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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
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

    private lateinit var user: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var fStore: FirebaseFirestore

    private var userID: String? = null

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

        fStore = FirebaseFirestore.getInstance()

        fStore.collection("users")
            .get()
            .addOnSuccessListener {
                for (doc in it) {
                    homeBinding.tvGreetName.text = doc["fullName"].toString()
                }
            }

        if (activity != null) {

            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[MainViewModel::class.java]

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
            }
        }
    }

    companion object {
        private const val CHOOSE_PDF_FILE = 1_000
    }

}