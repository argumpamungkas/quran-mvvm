package com.argumelar.quranmvvm.ui.detail

import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.quranmvvm.model.Constant
import com.argumelar.quranmvvm.model.DetailQuran
import com.argumelar.quranmvvm.model.RepositoryQuran
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.dsl.module
import kotlin.math.exp

val detailViewModel = module {
    factory { DetailViewModel(get()) }
}

class DetailViewModel(
    val repository: RepositoryQuran
) : ViewModel() {
    var mediaPlayer = MediaPlayer()

//    val title = "Detail Quran"
    val message by lazy { MutableLiveData<String>() }
    val quran by lazy { MutableLiveData<DetailQuran>()}
    val playSurah by lazy { MutableLiveData<Int>(0) }

    init {
        fetchDetail()
    }

    private fun fetchDetail(){
        viewModelScope.launch {
            try {
                val response = repository.fetchApiDetail(Constant.SURAH)
                quran.value = response
//                message.value = "Detail Quran"
            } catch (e: Exception){
                message.value = "GAGAL"
            }
        }
    }

    fun playSurah(url: String){
        viewModelScope.launch {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            if (!mediaPlayer.isPlaying){
//                Log.i("playSurah", "ON")
                message.value = "PLAY"
                try {
                    mediaPlayer.setDataSource(url)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(url)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                    }
                } catch (e:IOException){
                    e.printStackTrace()
                }
                playSurah.value = 1
            } else {
//                Log.i("playSurah", "OFF")
                message.value = "STOP"
                try {
                    mediaPlayer.pause()
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                } catch (e:IOException){
                    e.printStackTrace()
                }
                playSurah.value = 0
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.pause()
        mediaPlayer.stop()
        mediaPlayer.release()
        playSurah.value = 0
    }
}