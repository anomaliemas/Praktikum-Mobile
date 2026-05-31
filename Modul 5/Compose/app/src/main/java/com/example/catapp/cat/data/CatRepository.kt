package com.example.catapp.cat.data

import com.example.catapp.core.database.CatDao
import com.example.catapp.core.database.CatEntity
import com.example.catapp.core.network.ApiResult
import com.example.catapp.core.network.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CatRepository(
    private val apiService: CatApiService,
    private val catDao: CatDao
) {
    val catBreeds: Flow<List<CatEntity>> = catDao.getAllCats()

    suspend fun fetchBreedsFromNetwork() {
        val result = safeApiCall { apiService.getBreeds() }

        if (result is ApiResult.Success) {
            withContext(Dispatchers.IO) {
                result.data.forEach { dataKucing ->
                    val idGambar = dataKucing.reference_image_id
                    val tautanGambar = if (idGambar != null) {
                        "https://cdn2.thecatapi.com/images/${idGambar}.jpg"
                    } else {
                        "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
                    }

                    val entitasBaru = CatEntity(
                        name = dataKucing.name ?: "Tanpa Nama",
                        breed = dataKucing.name ?: "-",
                        age = 0,
                        description = dataKucing.description ?: "-",
                        imageUrl = tautanGambar
                    )
                    catDao.insertCat(entitasBaru)
                }
            }
        }
    }
}