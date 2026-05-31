package com.example.catapp.cat.data

import kotlinx.serialization.Serializable

@Serializable
data class CatBreed(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val reference_image_id: String? = null
)