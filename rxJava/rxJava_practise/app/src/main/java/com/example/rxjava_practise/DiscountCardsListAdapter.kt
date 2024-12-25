package com.example.rxjava_practise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_practise.data.remote.dto.DiscountCardDto
import com.example.rxjava_practise.databinding.DiscountCardsListItemBinding

class DiscountCardsListAdapter(
    private val discountCardsList: List<DiscountCardDto>
) : RecyclerView.Adapter<DiscountCardsListAdapter.DiscountCardsViewHolder>() {

    inner class DiscountCardsViewHolder(
        val binding: DiscountCardsListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountCardsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DiscountCardsListItemBinding
            .inflate(layoutInflater, parent, false)

        return DiscountCardsViewHolder(binding)
    }

    override fun getItemCount(): Int = discountCardsList.size

    override fun onBindViewHolder(holder: DiscountCardsViewHolder, position: Int) {
        holder.binding.apply {
            tvDiscountCardsShop.text = discountCardsList[position].storeName
        }
    }
}