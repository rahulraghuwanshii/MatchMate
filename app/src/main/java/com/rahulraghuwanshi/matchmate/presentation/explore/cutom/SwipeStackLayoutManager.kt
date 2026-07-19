package com.rahulraghuwanshi.matchmate.presentation.explore.cutom

import androidx.recyclerview.widget.RecyclerView

/**
 * Lays out only the top [visibleCount] items, centered, each one
 * slightly smaller and offset downward than the one above it —
 * giving the "peeking cards behind the top card" look.
 *
 * Position 0 is always the top (fully interactive) card.
 */
class SwipeStackLayoutManager(
    private val visibleCount: Int = 3,
    private val scaleStep: Float = 0.05f,
    private val translationYStepPx: Float = 24f,
) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        if (itemCount == 0) return

        val count = minOf(itemCount, visibleCount)
        // Layout back-to-front so the top card (position 0) is added last
        // and therefore drawn on top / receives touches first.
        for (position in count - 1 downTo 0) {
            val view = recycler.getViewForPosition(position)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            val left = (this.width - width) / 2
            val top = (this.height - height) / 2

            layoutDecorated(view, left, top, left + width, top + height)

            if (position != 0) {
                val depth = position.toFloat()
                view.scaleX = 1f - (scaleStep * depth)
                view.scaleY = 1f - (scaleStep * depth)
                view.translationY = translationYStepPx * depth
                view.translationX = 0f
                view.rotation = 0f
            } else {
                // top card starts neutral; SwipeTouchListener drives its transform live
                view.scaleX = 1f
                view.scaleY = 1f
                view.translationY = 0f
                view.translationX = 0f
                view.rotation = 0f
            }
        }
    }

    override fun canScrollVertically() = false
    override fun canScrollHorizontally() = false
}