package bhargava.kartik.gallerview.ui.gallerviewpackage.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bhargava.kartik.gallerview.R
import bhargava.kartik.gallerview.databinding.FragmentAllPicturesBinding
import bhargava.kartik.gallerview.ui.gallerviewpackage.adapters.UnsplashPhotoAdapter
import bhargava.kartik.gallerview.ui.gallerviewpackage.adapters.UnsplashPhotoLoadStateAdapter
import bhargava.kartik.gallerview.ui.gallerviewpackage.viewmodels.GalleryViewModel

class AllPicturesFragment : Fragment(R.layout.fragment_all_pictures) {

    private val viewModel: GalleryViewModel by activityViewModels()
    private var _binding: FragmentAllPicturesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllPicturesBinding.bind(view)
        val adapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() },
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }
        viewModel.getAllPhotos().observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}