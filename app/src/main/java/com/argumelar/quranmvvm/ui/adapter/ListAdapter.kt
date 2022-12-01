package com.argumelar.quranmvvm.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.quranmvvm.databinding.AdapterListBinding
import com.argumelar.quranmvvm.model.Constant
import com.argumelar.quranmvvm.model.QuranModel

class ListAdapter(
    val qurans: ArrayList<QuranModel>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    class ViewHolder(val binding: AdapterListBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quran = qurans[position]
        holder.binding.tvNamaSurat.text = quran.name + " (" + quran.number_of_surah.toString() +")"
        holder.binding.tvTranslate.text = quran.name_translations!!.id + " (" + quran.number_of_ayah.toString() + ")"
        holder.itemView.setOnClickListener {
            Constant.SURAH = quran.number_of_surah!!
            listener.onClick(quran)
        }
    }

    override fun getItemCount(): Int {
        return qurans.size
    }

    fun setData(newQuran: ArrayList<QuranModel>){
        qurans.clear()
        qurans.addAll(newQuran)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(quran: QuranModel)
    }
}