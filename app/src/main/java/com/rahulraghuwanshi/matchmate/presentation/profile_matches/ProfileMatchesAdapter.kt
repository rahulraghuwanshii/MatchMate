package com.rahulraghuwanshi.matchmate.presentation.profile_matches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahulraghuwanshi.matchmate.databinding.ItemMatchUserBinding
import com.rahulraghuwanshi.matchmate.presentation.explore.model.Matched
import com.rahulraghuwanshi.matchmate.presentation.explore.model.UserData

class ProfileMatchesAdapter :
    ListAdapter<UserData, ProfileMatchesAdapter.ProfileMatchesViewHolder>(DiffCallback()) {

    inner class ProfileMatchesViewHolder(
        private val binding: ItemMatchUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userData: UserData) {

            binding.textNameAge.text = userData.title
            binding.textLocation.text = userData.subtitle

            Glide.with(binding.imagePhoto).load(userData.imageUrl).centerCrop()
                .into(binding.imagePhoto)

            binding.badgeOnline.visibility = if (userData.online) View.VISIBLE else View.GONE
            binding.iconVerified.visibility = if (userData.verified) View.VISIBLE else View.GONE

            when (userData.matched) {
                Matched.LIKE -> {
                    binding.stampLike.isVisible = true
                    binding.stampNope.isVisible = false
                }

                Matched.DISLIKE -> {
                    binding.stampLike.isVisible = false
                    binding.stampNope.isVisible = true
                }

                Matched.NONE -> {
                    binding.stampLike.isVisible = false
                    binding.stampNope.isVisible = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMatchesViewHolder {
        val binding = ItemMatchUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileMatchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileMatchesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<UserData>() {

        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }
    }
}