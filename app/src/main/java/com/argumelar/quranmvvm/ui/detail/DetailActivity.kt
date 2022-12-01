package com.argumelar.quranmvvm.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.argumelar.quranmvvm.databinding.ActivityDetailBinding
import com.argumelar.quranmvvm.model.QuranModel
import com.argumelar.quranmvvm.ui.adapter.DetailAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val moduleDetail = module {
    factory { DetailActivity() }
}

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"

    lateinit var binding: ActivityDetailBinding
    private val viewModel : DetailViewModel by viewModel()
    private val namaSurat by lazy {
        intent.getSerializableExtra("nama_surat") as QuranModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = namaSurat.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.rvList.adapter = detailAdapter
        viewModel.quran.observe(this, Observer {
            binding.pbLoading.visibility = if (it.verses!!.isEmpty()) View.VISIBLE else View.GONE
            detailAdapter.setData(it.verses)
        })

        viewModel.message.observe(this, Observer {
            it?.let {
               Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        })

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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}