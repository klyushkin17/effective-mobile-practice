package com.example.rxjava_practise.data.remote.dto

import com.squareup.moshi.Json

data class VacancyDto(
    @field:Json(name = "id") val vacancyId: String,
    @field:Json(name = "title") val vacancyTitle: String,
)
