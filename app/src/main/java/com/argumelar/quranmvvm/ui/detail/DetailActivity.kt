package com.argumelar.quranmvvm.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.quranmvvm.R
import com.argumelar.quranmvvm.databinding.ActivityDetailBinding
import com.argumelar.quranmvvm.databinding.SurahDescBinding
import com.argumelar.quranmvvm.model.QuranModel
import com.argumelar.quranmvvm.ui.adapter.DetailAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val moduleDetail = module {
    factory { DetailActivity() }
}

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"

    private lateinit var binding: ActivityDetailBinding
    private lateinit var bindingContent: SurahDescBinding
    private val viewModel: DetailViewModel by viewModel()
    private val namaSurat by lazy {
        intent.getSerializableExtra("nama_surat") as QuranModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        bindingContent = binding.containerDesc
        setContentView(binding.root)


        supportActionBar!!.title = namaSurat.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.rvList.adapter = detailAdapter
        viewModel.quran.observe(this) {
            binding.pbLoading.visibility = if (it.verses!!.isEmpty()) View.VISIBLE else View.GONE
            detailAdapter.setData(it.verses)
            bindingContent.namaSurat.text = it.name
            bindingContent.artiSurat.text = it.name_translations!!.id
            bindingContent.nomorSurat.text = it.number_of_surah.toString()
            bindingContent.jumlahAyat.text = it.number_of_ayah.toString()
            bindingContent.diturunkan.text = it.place
            bindingContent.tipeSurat.text = it.type

            bindingContent.arrowButton.setOnClickListener {
                expandData()
            }

            viewModel.playSurah.observe(this,) {
                if (it == 0){
                    binding.btnPlay.setImageResource(R.drawable.ic_play)
                } else {
                    binding.btnPlay.setImageResource(R.drawable.ic_stop)
                }
            }
            binding.btnPlay.setOnClickListener {
                viewModel.playSurah(namaSurat.recitation.toString())
            }
            scrollListener()
        }

        viewModel.message.observe(this) {
            it?.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        }

//        detailQuran.let {
//            binding.tvSurat.text = it.number_of_surah.toString()
//            binding.tvNamaSurat.text = it.name
//            binding.tvArtiSurat.text = it.name_translations!!.id
//            binding.tvJumlahAyat.text = it.number_of_ayah.toString()
//        }
    }

    private val detailAdapter by lazy {
        DetailAdapter(arrayListOf())
    }

    private fun scrollListener(){

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY + 10){
                binding.btnPlay.hide()
            }

            if (scrollY < oldScrollY - 10){
                binding.btnPlay.show()
            }

            if (scrollY == 0){
                binding.btnPlay.show()
            }
        })

    }

    private fun expandData() {
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        if (bindingContent.hiddenView.visibility == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(bindingContent.cardDesc, AutoTransition())
            bindingContent.hiddenView.visibility = View.GONE
            bindingContent.arrowButton.setImageResource(R.drawable.ic_arrow_down)
        } else {
            TransitionManager.beginDelayedTransition(bindingContent.cardDesc, AutoTransition())
            bindingContent.hiddenView.visibility = View.VISIBLE
            bindingContent.arrowButton.setImageResource(R.drawable.ic_arrow_up)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}