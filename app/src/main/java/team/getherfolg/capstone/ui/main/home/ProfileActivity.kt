@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import team.getherfolg.capstone.R
import team.getherfolg.capstone.databinding.ActivityProfileBinding
import team.getherfolg.capstone.ui.authentication.AuthenticationActivity
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileBinding: ActivityProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var imageURI: Uri

    companion object {
        const val REQ_CAMERA = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)

        profileBinding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            imageProfile.setOnClickListener {
                takeMedia()
            }
        }


        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        if (user != null) {
            if (user.photoUrl != null) {
                Glide.with(this).load(user.photoUrl).into(profileBinding.imageProfile)
            } else {
                Glide.with(this).load(R.drawable.ic_profile).into(profileBinding.imageProfile)
            }

            profileBinding.apply {
                etEmail.text = user.email
                if (user.isEmailVerified) {
                    tvVerif.visibility = GONE
                    verifTrue.visibility = VISIBLE
                    verifFalse.visibility = GONE
                }
            }

        }

        profileBinding.verifFalse.setOnClickListener {
            user?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Email verification has been send", Toast.LENGTH_SHORT)
                        .show()
                    mAuth.signOut()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        profileBinding.btnSave.setOnClickListener {
            val img = when {
                ::imageURI.isInitialized -> imageURI
                user?.photoUrl == null -> R.drawable.ic_profile
                else -> user.photoUrl
            }

            UserProfileChangeRequest.Builder()
                .setPhotoUri(img as Uri?).build().also { profile ->
                    user?.updateProfile(profile)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Snackbar.make(
                                profileBinding.root,
                                "Profile Updated",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            Snackbar.make(
                                profileBinding.root,
                                "Failed to Update Profile",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }

        profileBinding.btnLogout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun takeMedia() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager).also {
                startActivityForResult(intent, REQ_CAMERA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            uploadImage(bitmap)
        }
    }

    private fun uploadImage(img: Bitmap) {
        val output = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img/${mAuth.currentUser?.uid}")

        img.compress(Bitmap.CompressFormat.JPEG, 100, output)
        val image = output.toByteArray()

        ref.putBytes(image).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener { taskUri ->
                    taskUri.result?.let {
                        imageURI = it
                        profileBinding.imageProfile.setImageBitmap(img)
                    }
                }
            }
        }
    }
}