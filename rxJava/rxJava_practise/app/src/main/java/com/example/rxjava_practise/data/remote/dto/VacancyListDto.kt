package com.example.rxjava_practise.data.remote.dto

import com.squareup.moshi.Json
import io.reactivex.rxjava3.core.Observable

data class VacancyListDto(
    @field:Json(name = "vacancies") val vacancyList: List<VacancyDto>,
)
