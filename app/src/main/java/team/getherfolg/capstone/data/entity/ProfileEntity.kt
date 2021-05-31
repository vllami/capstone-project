package team.getherfolg.capstone.data.entity

import com.google.gson.annotations.SerializedName

data class ProfileEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("first_air_date")
    val releaseDate: String
)