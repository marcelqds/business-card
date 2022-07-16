package com.mqds.cartaovisitabootcamp.ui

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mqds.cartaovisitabootcamp.data.BusinessCard
import com.mqds.cartaovisitabootcamp.databinding.ItemCardsBinding

class BusinessCardAdapter: ListAdapter<BusinessCard, BusinessCardAdapter.ViewHolderCard>(DiffCallback()) {
    var listenerShare: (View) -> Unit = {}

    inner class ViewHolderCard(private val binding: ItemCardsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind( items : BusinessCard){
            binding.tvName.text = items.name
            binding.tvEmail.text = items.email
            binding.tvPhoneNumber.text = items.phoneNumber
            binding.tvCompany.text = items.company
            binding.cvCard.setBackgroundColor(Color.parseColor(items.backgroundColor))
            binding.cvCard.setOnClickListener{
                listenerShare(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCard {
        val binding = ItemCardsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderCard(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderCard, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback : DiffUtil.ItemCallback<BusinessCard>(){
    override fun areItemsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem == newItem
    override fun areContentsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem.id == newItem.id
}