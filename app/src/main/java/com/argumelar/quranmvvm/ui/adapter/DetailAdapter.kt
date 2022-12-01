package com.argumelar.quranmvvm.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.quranmvvm.databinding.AdapterDetailBinding
import com.argumelar.quranmvvm.model.Verses

class DetailAdapter(val surahs : ArrayList<Verses>): RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(val binding: AdapterDetailBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surah = surahs[position]
        holder.binding.textSurat.text = surah.text
        holder.binding.tvTranslate.text = surah.number.toString() + ". " + surah.translation_id

    }

    override fun getItemCount(): Int {
        return surahs.size
    }

    fun setData(newSurat: List<Verses>){
        surahs.clear()
        surahs.addAll(newSurat)
        notifyDataSetChanged()
    }
}