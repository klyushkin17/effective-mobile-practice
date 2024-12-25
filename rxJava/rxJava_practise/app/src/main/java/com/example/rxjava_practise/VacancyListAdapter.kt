package com.example.rxjava_practise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_practise.data.remote.dto.VacancyDto
import com.example.rxjava_practise.databinding.VacanciesListItemBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject


class VacancyListAdapter(
    private val vacancyList: List<VacancyDto>,
) : RecyclerView.Adapter<VacancyListAdapter.VacanciesViewHolder>() {

    private val vacancyClickSubject = PublishSubject.create<Int>()

    @Suppress("DEPRECATION")
    inner class VacanciesViewHolder(
        val binding: VacanciesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            /*binding.tvVacanciesTitle.clicks().map {
                vacancyList[layoutPosition]
            }.subscribe(vacancyClickSubject)*/
            binding.tvVacanciesTitle.setOnClickListener {
                vacancyClickSubject.onNext(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacanciesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VacanciesListItemBinding
            .inflate(layoutInflater, parent, false)
        return VacanciesViewHolder(binding)
    }

    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: VacanciesViewHolder, position: Int) {
        holder.binding.apply {
            tvVacanciesTitle.text = vacancyList[position].vacancyTitle
        }
    }

    val vacancyClickEvent: Observable<Int> = vacancyClickSubject
}