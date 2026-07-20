package com.rahulraghuwanshi.matchmate.presentation.profile_matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.rahulraghuwanshi.matchmate.databinding.FragmentProfileMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileMatchesFragment : Fragment() {


    private val viewModel: ProfileMatchesViewModel by viewModels()

    private var _binding: FragmentProfileMatchesBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileMatchesAdapter: ProfileMatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchMatchedUsers()
        setUp()
        collectFlow()
    }

    private fun setUp() {
        profileMatchesAdapter = ProfileMatchesAdapter()
        binding.rvMatchedUsers.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMatchedUsers.adapter = profileMatchesAdapter
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userMatchDetailsFlow.collectLatest {
                    profileMatchesAdapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}