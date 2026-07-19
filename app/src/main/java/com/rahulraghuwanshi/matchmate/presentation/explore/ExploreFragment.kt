package com.rahulraghuwanshi.matchmate.presentation.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.rahulraghuwanshi.matchmate.databinding.FragmentExploreBinding
import com.rahulraghuwanshi.matchmate.presentation.explore.cutom.SwipeStackLayoutManager
import com.rahulraghuwanshi.matchmate.presentation.explore.model.CardItem
import com.rahulraghuwanshi.matchmate.presentation.explore.model.toCardItem
import com.rahulraghuwanshi.matchmate.presentation.util.NetworkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    private val viewModel: ExploreViewModel by viewModels()

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CardAdapter

    @Inject
    lateinit var networkManager: NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val view = binding.root


        val recyclerView = binding.recyclerCards
        val btnAccept = binding.btnAccept
        val btnDecline = binding.btnDecline

        // drag needs to travel ~35% of screen width before it counts as a swipe
        val swipeThresholdPx = resources.displayMetrics.widthPixels * 0.35f

        adapter = CardAdapter(
            swipeThresholdPx = swipeThresholdPx,
            networkManager = networkManager,
            onAccepted = { item -> handleSwipe(item, accepted = true) },
            onDeclined = { item -> handleSwipe(item, accepted = false) }
        )

        recyclerView.layoutManager = SwipeStackLayoutManager(visibleCount = 3)
        recyclerView.adapter = adapter

        btnAccept.setOnClickListener {
            animateTopCardOff(recyclerView, accepted = true)
        }
        btnDecline.setOnClickListener {
            animateTopCardOff(recyclerView, accepted = false)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlow()
    }

    private fun getData() {
        viewModel.fetchUserDetails()
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userDetailFlow.collectLatest {
                    it?.let { it1 -> adapter.submitData(it1) }
                }
            }
        }
    }

    /**
     * Called after a swipe is decided - either dragged past threshold (drag
     * flight already happened live) or after the button-triggered fly-off
     * animation finishes.
     */
    private fun handleSwipe(item: CardItem, accepted: Boolean) {
        val shouldRefresh = viewModel.markSwiped(item, accepted)
        if (shouldRefresh) {
            // Re-runs the PagingSource; since swipedIds now excludes this
            // item, Paging3's DiffUtil sees it as removed and the adapter
            // animates it out on its own.
            adapter.refresh()
        }
    }

    /** Called by the tap buttons - animates the top card off screen, then removes it. */
    private fun animateTopCardOff(recyclerView: RecyclerView, accepted: Boolean) {
        val topView = recyclerView.getChildAt(recyclerView.childCount - 1) ?: return
        val item = adapter.peek(0) ?: return

        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val targetX = if (accepted) screenWidth else -screenWidth
        val targetRotation = if (accepted) 20f else -20f

        topView.animate()
            .translationX(targetX)
            .rotation(targetRotation)
            .setDuration(250)
            .withEndAction { handleSwipe(item.toCardItem(), accepted) }
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}