package com.argumelar.quranmvvm.network

import com.argumelar.quranmvvm.model.DetailQuran
import com.argumelar.quranmvvm.model.QuranModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndpoint {

    @GET("quran.json")
    suspend fun get(): List<QuranModel>

    @GET("surah/{number_of_surah}.json")
    suspend fun getDetail(
        @Path("number_of_surah") surah: Int
    ): DetailQuran

}