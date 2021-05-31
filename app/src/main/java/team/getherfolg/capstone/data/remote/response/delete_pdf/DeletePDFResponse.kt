package team.getherfolg.capstone.data.remote.response.delete_pdf

import com.google.gson.annotations.SerializedName

data class DeletePDFResponse(
    @SerializedName("results")
    val result: List<DeletePDFRemote>
)