package com.example.rxjava_practise.data.remote

import com.example.rxjava_practise.data.remote.dto.VacancyListDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface HhRuApi {
    @GET("vacancies.json")
    fun getVacancies(): Observable<VacancyListDto>
}