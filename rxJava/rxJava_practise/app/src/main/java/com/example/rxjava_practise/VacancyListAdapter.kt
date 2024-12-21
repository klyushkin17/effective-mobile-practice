package com.example.rxjava_practise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_practise.data.remote.dto.VacancyDto
import com.example.rxjava_practise.databinding.VacanciesListItemBinding

class VacancyListAdapter (
    private val vacancyList: List<VacancyDto>,
) : RecyclerView.Adapter<VacancyListAdapter.VacanciesViewHolder>() {

    interface Listener {
        fun onItemClick(android: VacancyDto)
    }

    inner class VacanciesViewHolder(
        val binding: VacanciesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

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
}