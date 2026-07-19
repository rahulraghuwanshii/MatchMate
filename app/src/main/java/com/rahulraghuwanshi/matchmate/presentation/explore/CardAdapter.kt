package com.rahulraghuwanshi.matchmate.presentation.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.rahulraghuwanshi.matchmate.R
import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails
import com.rahulraghuwanshi.matchmate.presentation.explore.cutom.SwipeTouchListener
import com.rahulraghuwanshi.matchmate.presentation.explore.model.CardItem

 import com.bumptech.glide.Glide
import com.rahulraghuwanshi.matchmate.presentation.explore.model.toCardItem
import com.rahulraghuwanshi.matchmate.presentation.util.NetworkManager

class CardAdapter(
    private val swipeThresholdPx: Float,
    private val networkManager: NetworkManager,
    private val onAccepted: (CardItem) -> Unit,
    private val onDeclined: (CardItem) -> Unit
) :
    PagingDataAdapter<UserDetails, CardAdapter.CardViewHolder>(UserDetailDiffCallBack()) {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imagePhoto)
        val nameAge: TextView = view.findViewById(R.id.textNameAge)
        val location: TextView = view.findViewById(R.id.textLocation)
        val badgeOnline: TextView = view.findViewById(R.id.badgeOnline)
        val iconVerified: ImageView = view.findViewById(R.id.iconVerified)
        val tagContainer: FlexboxLayout = view.findViewById(R.id.tagContainer)
        val stampLike: View = view.findViewById(R.id.stampLike)
        val stampNope: View = view.findViewById(R.id.stampNope)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_detail, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val userDetail = getItem(position) ?: return
        val item = userDetail.toCardItem()

        holder.nameAge.text = item.title
        holder.location.text = item.subtitle

         Glide.with(holder.image).load(item.imageUrl).centerCrop().into(holder.image)

        holder.badgeOnline.visibility = if (item.online) View.VISIBLE else View.GONE
        holder.iconVerified.visibility = if (item.verified) View.VISIBLE else View.GONE

        bindTags(holder, item.interests)

        // reset any leftover transform/alpha from a previous bind of this recycled view
        holder.itemView.apply {
            translationX = 0f
            translationY = 0f
            rotation = 0f
        }
        holder.stampLike.alpha = 0f
        holder.stampNope.alpha = 0f

        if (position == 0) {
            holder.itemView.setOnTouchListener(
                SwipeTouchListener(
                    stampLike = holder.stampLike,
                    stampNope = holder.stampNope,
                    swipeThresholdPx = swipeThresholdPx,
                    networkManager = networkManager,
                    onAccepted = { onAccepted(item) },
                    onDeclined = { onDeclined(item) }
                )
            )
        } else {
            holder.itemView.setOnTouchListener(null)
        }
    }

    private fun bindTags(holder: CardViewHolder, interests: List<String>) {
        holder.tagContainer.removeAllViews()
        val context = holder.tagContainer.context
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
            holder.tagContainer.addView(chip)
        }
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