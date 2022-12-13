package com.argumelar.quranmvvm.ui.home

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.argumelar.quranmvvm.databinding.ActivityHomeBinding
import com.argumelar.quranmvvm.model.QuranModel
import com.argumelar.quranmvvm.ui.adapter.ListAdapter
import com.argumelar.quranmvvm.ui.detail.DetailActivity
import okio.IOException
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeActivity() }
}

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private val viewModel: MainViewModel by viewModel()
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.adapter = listAdapter
        viewModel.quran.observe(this) {
            binding.pbLoading.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            listAdapter.setData(it as ArrayList<QuranModel>)
        }

        viewModel.message.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }

    private val listAdapter by lazy {
        ListAdapter(arrayListOf(), object : ListAdapter.OnAdapterListener {
            override fun onClick(quran: QuranModel) {
                startActivity(
                    Intent(applicationContext, DetailActivity::class.java)
                        .putExtra("nama_surat", quran)
                )
//                Toast.makeText(requireContext(), quran.name, Toast.LENGTH_SHORT).show()
            }

            override fun onPlaySurah(quran: QuranModel) {
                Toast.makeText(applicationContext, "Play surah ${quran.name}", Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                mediaPlayer!!.setDataSource(quran.recitation.toString())
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()


//                if (!mediaPlayer!!.isPlaying) {
//                    Toast.makeText(applicationContext, "Play surah ${quran.name}", Toast.LENGTH_SHORT).show()
//                    try {
//                        mediaPlayer!!.setDataSource(quran.recitation.toString())
//                        mediaPlayer!!.prepare()
//                        mediaPlayer!!.start()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    try {
//                        Toast.makeText(applicationContext, "Stop surah", Toast.LENGTH_SHORT).show()
//                        mediaPlayer!!.pause()
//                        mediaPlayer!!.stop()
//                        mediaPlayer!!.reset()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                }
            }

        })
    }
}