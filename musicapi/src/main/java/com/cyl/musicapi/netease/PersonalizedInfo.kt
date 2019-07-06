package com.cyl.musicapi.netease

import com.google.gson.annotations.SerializedName

data class PersonalizedInfo(@SerializedName("result")
                            val result: MutableList<PersonalizedItem>?,
                            @SerializedName("code")
                            val code: Int = 0,
                            @SerializedName("category")
                            val category: Int = 0,
                            @SerializedName("hasTaste")
                            val hasTaste: Boolean = false)


data class PersonalizedItem(@SerializedName("id")
                            val id: Long = 0,
                            @SerializedName("type")
                            val type: Int = 0,
                            @SerializedName("name")
                            val name: String = "",
                            @SerializedName("copywriter")
                            val copywriter: String = "",
                            @SerializedName("picUrl")
                            val picUrl: String = "",
                            @SerializedName("canDislike")
                            val canDislike: Boolean = false,
                            @SerializedName("playCount")
                            val playCount: Float = 0f,
                            @SerializedName("trackCount")
                            val trackCount: Int = 0,
                            @SerializedName("highQuality")
                            val highQuality: Boolean = false,

                            @SerializedName("duration")
                            val duration: Int = 0,
                            @SerializedName("subed")
                            val subed: Boolean = false,
                            @SerializedName("artists")
                            val artists: MutableList<ArtistsItem>?,
                            @SerializedName("artistName")
                            val artistName: String = "",
                            @SerializedName("artistId")
                            val artistId: Int = 0,

                            @SerializedName("alg")
                            val alg: String = "")
