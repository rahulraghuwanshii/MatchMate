package com.rahulraghuwanshi.matchmate.presentation.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.rahulraghuwanshi.matchmate.R
import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails
import com.rahulraghuwanshi.matchmate.databinding.ItemUserDetailBinding
import com.rahulraghuwanshi.matchmate.presentation.explore.cutom.SwipeTouchListener
import com.rahulraghuwanshi.matchmate.presentation.explore.model.UserData
import com.rahulraghuwanshi.matchmate.presentation.explore.model.toUserData

class ExplorePageAdapter(
    private val swipeThresholdPx: Float,
    private val onAccepted: (UserData) -> Unit,
    private val onDeclined: (UserData) -> Unit
) :
    PagingDataAdapter<UserDetails, ExplorePageAdapter.ExplorePageViewHolder>(UserDetailDiffCallBack()) {

    class ExplorePageViewHolder(private val binding: ItemUserDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            userDetail: UserDetails?,
            swipeThresholdPx: Float,
            onAccepted: (UserData) -> Unit,
            onDeclined: (UserData) -> Unit
        ) {
            val item = userDetail?.toUserData() ?: return

            binding.textNameAge.text = item.title
            binding.textLocation.text = item.subtitle

            Glide.with(binding.imagePhoto).load(item.imageUrl).centerCrop().into(binding.imagePhoto)

            binding.badgeOnline.visibility = if (item.online) View.VISIBLE else View.GONE
            binding.iconVerified.visibility = if (item.verified) View.VISIBLE else View.GONE

            bindTags(binding, item.interests)

            // reset any leftover transform/alpha from a previous bind of this recycled view
            binding.root.apply {
                translationX = 0f
                translationY = 0f
                rotation = 0f
            }
            binding.stampLike.alpha = 0f
            binding.stampNope.alpha = 0f

            if (position == 0) {
                binding.root.setOnTouchListener(
                    SwipeTouchListener(
                        stampLike = binding.stampLike,
                        stampNope = binding.stampNope,
                        swipeThresholdPx = swipeThresholdPx,
                        onAccepted = { onAccepted(item) },
                        onDeclined = { onDeclined(item) }
                    )
                )
            } else {
                binding.root.setOnTouchListener(null)
            }
        }

        private fun bindTags(binding: ItemUserDetailBinding, interests: List<String>) {
            binding.tagContainer.removeAllViews()
            val context = binding.tagContainer.context
            interests.forEach { interest ->
                val chip = TextView(context).apply {
                    text = interest
                    textSize = 12f
                    setPadding(28, 12, 28, 12)
                    setBackgroundResource(R.drawable.bg_tag_chip)
                    val params = FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 12, 8)
                    layoutParams = params
                }
                binding.tagContainer.addView(chip)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorePageViewHolder {
        return ExplorePageViewHolder(
            ItemUserDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExplorePageViewHolder, position: Int) {
        holder.bind(getItem(position), swipeThresholdPx, onAccepted, onDeclined)
    }
}

class UserDetailDiffCallBack : DiffUtil.ItemCallback<UserDetails>() {
    override fun areItemsTheSame(oldItem: UserDetails, newItem: UserDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserDetails, newItem: UserDetails): Boolean {
        return oldItem == newItem
    }
}