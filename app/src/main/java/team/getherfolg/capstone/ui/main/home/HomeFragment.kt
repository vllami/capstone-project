@file:Suppress("DEPRECATION")

package team.getherfolg.capstone.ui.main.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.okhttp.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.getherfolg.capstone.databinding.FragmentHomeBinding
import team.getherfolg.capstone.databinding.FragmentHomeBinding.inflate
import team.getherfolg.capstone.networking.SuitableClient
import team.getherfolg.capstone.ui.main.home.profile.ProfileActivity
import java.io.InputStream
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

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
                Intent(ACTION_GET_CONTENT).also {
                    it.apply {
                        type = "application/pdf"
                        addCategory(CATEGORY_OPENABLE)
                        createChooser(this, "")
                        startActivityForResult(this, CHOOSE_PDF_FILE)
                    }
                }
            }
            btnUpload.setOnClickListener {
                uploadDocument()
            }
        }

    }

    private fun uploadDocument() {
        SuitableClient.getService().uploadPDF(encodedPDF)
            .enqueue(object : Callback<ArrayList<RequestBody>> {
                override fun onResponse(
                    call: Call<ArrayList<RequestBody>>,
                    response: Response<ArrayList<RequestBody>>
                ) {
                    Toast.makeText(activity, "Sending Success", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ArrayList<RequestBody>>, t: Throwable) {
                    Toast.makeText(activity, "Sending Failed", Toast.LENGTH_SHORT).show()
                }

            })
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_PDF_FILE && resultCode == RESULT_OK && data != null) {
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