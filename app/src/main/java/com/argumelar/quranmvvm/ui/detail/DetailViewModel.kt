package com.argumelar.quranmvvm.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.quranmvvm.model.Constant
import com.argumelar.quranmvvm.model.DetailQuran
import com.argumelar.quranmvvm.model.RepositoryQuran
import kotlinx.coroutines.launch
import org.koin.dsl.module
import kotlin.math.exp

val detailViewModel = module {
    factory { DetailViewModel(get()) }
}

class DetailViewModel(
    val repository: RepositoryQuran
) : ViewModel() {

//    val title = "Detail Quran"
    val message by lazy { MutableLiveData<String>() }
    val quran by lazy { MutableLiveData<DetailQuran>()}
    val expanded by lazy { MutableLiveData<Int>()}

    init {
        fetchDetail()
        expanded.value = 0
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

}