package com.example.catapp.cat.data

import retrofit2.http.GET

interface CatApiService {
    @GET("v1/breeds")
    suspend fun getBreeds(): List<CatBreed>
}