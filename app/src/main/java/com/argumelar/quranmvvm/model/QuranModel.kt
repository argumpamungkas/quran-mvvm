package com.argumelar.quranmvvm.model

import java.io.Serializable

data class QuranModel(
    val name: String?,
    val name_translations: NameTranslation?,
    val number_of_ayah: Int?,
    val number_of_surah: Int?,
    val place: String?,
    val recitation: String?,
    val type: String?
) : Serializable

data class NameTranslation(
    val id: String?
) : Serializable
