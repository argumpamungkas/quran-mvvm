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

    var mediaPlayer = MediaPlayer()

    //    val title = "Quran Aplikasi"
    val message by lazy { MutableLiveData<String>() }
    val quran by lazy { MutableLiveData<List<QuranModel>>() }
    val playSurah by lazy { MutableLiveData<Int>() }

    init {
        message.value = null
        fetch()
        playSurah.value = 0
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

    fun playSurah(url: String){
        viewModelScope.launch {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
            if (!mediaPlayer.isPlaying){
//                Log.i("playSurah", "ON")
                message.value = "PLAY"
                try {
                    mediaPlayer.setDataSource(url)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e:IOException){
                    e.printStackTrace()
                }

            } else {
//                Log.i("playSurah", "OFF")
                message.value = "STOP"
                try {
                    Log.i("playSurah", "OFF NIH")
                    mediaPlayer.pause()
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                } catch (e:IOException){
                    e.printStackTrace()
                }
            }
        }
    }
}