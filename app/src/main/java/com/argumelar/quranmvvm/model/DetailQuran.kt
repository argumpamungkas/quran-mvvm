package com.argumelar.quranmvvm.model

data class DetailQuran(
    val name: String?,
    val name_translations: Translations?,
    val number_of_ayah: Int?,
    val number_of_surah: Int?,
    val place: String?,
    val type: String?,
    val verses: List<Verses>?,
)

data class Translations(
    val id: String?
)

data class Verses(
    val number: Int?,
    val text: String?,
    val translation_id: String?
)
