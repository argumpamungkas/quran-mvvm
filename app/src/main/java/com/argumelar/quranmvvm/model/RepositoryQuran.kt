package com.argumelar.quranmvvm.model

import com.argumelar.quranmvvm.network.ApiEndpoint
import org.koin.dsl.module
import retrofit2.Call

val repositoryModule = module {
    factory { RepositoryQuran(get()) }
}

class RepositoryQuran (
    private val api: ApiEndpoint
    ) {

    suspend fun fetchApi() : List<QuranModel> {
        return api.get()
    }

    suspend fun fetchApiDetail(
        number_of_surah: Int
    ) : DetailQuran {
        return api.getDetail(
            number_of_surah
        )
    }
}