package com.argumelar.quranmvvm.ui.home

import android.media.AudioManager
import android.media.MediaPlayer
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.quranmvvm.model.QuranModel
import com.argumelar.quranmvvm.model.RepositoryQuran
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.dsl.module

val homeViewModel = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(
    val repository: RepositoryQuran,
) : ViewModel() {

    //    val title = "Quran Aplikasi"
    val message by lazy { MutableLiveData<String>() }
    val quran by lazy { MutableLiveData<List<QuranModel>>() }


    init {
        message.value = null
        fetch()

    }

    private fun fetch() {
        viewModelScope.launch {
            try {
                val response = repository.fetchApi()
                quran.value = response
//                message.value = "Quran MVVM"
            } catch (e: Exception) {
//                message.value = "GAGAL"
            }
        }
    }


}