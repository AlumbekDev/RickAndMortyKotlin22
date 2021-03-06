package com.example.rickandmortykotlin22.ui.fragment.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortykotlin22.databinding.FragmentEpisodeBinding
import com.example.rickandmortykotlin22.ui.adapter.EpisodeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeFragment : Fragment() {
    private val viewModel: EpisodeViewModel by viewModels()
    private lateinit var binding: FragmentEpisodeBinding
    private val episodeAdapter = EpisodeAdapter(this::onItemClickRecyclerItem)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupRequests()
    }

    private fun initialize() {
        setupCharacterRecycler()
    }

    private fun setupCharacterRecycler() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = episodeAdapter.withLoadStateFooter(
            com.example.rickandmortykotlin22.ui.adapter.paging.LoadStateAdapter {
                episodeAdapter.retry()
            }
        )

        episodeAdapter.addLoadStateListener { loadStates ->
            recyclerView.isVisible = loadStates.refresh is LoadState.NotLoading
        }


        swipeRefresh.setOnRefreshListener {
            episodeAdapter.retry()
            swipeRefresh.isRefreshing = false
            setupRequests()
        }
    }

    private fun setupRequests() {
        viewModel.fetchEpisode().observe(requireActivity(), {
            this.lifecycleScope.launch {
                episodeAdapter.submitData(it)
            }
        })
    }

    private fun onItemClickRecyclerItem(id: Int){
        findNavController().navigate(
            EpisodeFragmentDirections.actionEpisodeFragmentToEpisodeDetailFragment(id)
        )
    }
}