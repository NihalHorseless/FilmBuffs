package com.example.filmbuffs.data.remote.models.castmodel


import com.google.gson.annotations.SerializedName

data class CastList(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)