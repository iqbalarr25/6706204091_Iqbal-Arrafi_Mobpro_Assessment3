package org.d3if4091.kalkulatoramoeba

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if4091.kalkulatoramoeba.model.Creator
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val USERNAME_GITHUB = "iqbalarr25"

private const val BASE_URL = "https://api.github.com/users/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CreatorApiService {
    @GET(USERNAME_GITHUB)
    suspend fun getCreator(): Creator
}

object CreatorApi {
    val service: CreatorApiService by lazy {
        retrofit.create(CreatorApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }