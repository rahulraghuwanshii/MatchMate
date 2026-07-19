package com.rahulraghuwanshi.matchmate.presentation.explore.cutom

import android.Manifest
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresPermission
import com.rahulraghuwanshi.matchmate.presentation.util.NetworkManager
import kotlin.math.abs

/**
 * Attach to the top card's itemView (in onBindViewHolder, only for position 0).
 * Drives translation + rotation live, shows LIKE/NOPE stamps proportionally,
 * and fires onAccepted/onDeclined when the drag crosses [swipeThresholdPx].
 */
class SwipeTouchListener(
    private val stampLike: View,
    private val stampNope: View,
    private val swipeThresholdPx: Float,
    private val networkManager: NetworkManager,
    private val onAccepted: () -> Unit,
    private val onDeclined: () -> Unit
) : View.OnTouchListener {

    private var startX = 0f
    private var startY = 0f
    private var dX = 0f
    private var dY = 0f

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.rawX
                startY = event.rawY
                dX = view.translationX
                dY = view.translationY
                return true
            }

            MotionEvent.ACTION_MOVE -> {

                if (!networkManager.isNetworkAvailable()) return true

                val deltaX = event.rawX - startX
                val deltaY = event.rawY - startY
                view.translationX = dX + deltaX
                view.translationY = dY + deltaY
                // rotate up to ~15 degrees based on horizontal drag distance
                val rotationDegrees = (view.translationX / swipeThresholdPx) * 15f
                view.rotation = rotationDegrees.coerceIn(-15f, 15f)

                val progress = (abs(view.translationX) / swipeThresholdPx).coerceIn(0f, 1f)
                if (view.translationX > 0) {
                    stampLike.alpha = progress
                    stampNope.alpha = 0f
                } else {
                    stampNope.alpha = progress
                    stampLike.alpha = 0f
                }
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                when {
                    view.translationX > swipeThresholdPx -> {
                        onAccepted()
                    }

                    view.translationX < -swipeThresholdPx -> {
                        onDeclined()
                    }

                    else -> {
                        // snap back to center
                        view.animate()
                            .translationX(0f)
                            .translationY(0f)
                            .rotation(0f)
                            .setDuration(200)
                            .start()
                        stampLike.animate().alpha(0f).setDuration(150).start()
                        stampNope.animate().alpha(0f).setDuration(150).start()
                    }
                }
                return true
            }
        }
        return false
    }
}