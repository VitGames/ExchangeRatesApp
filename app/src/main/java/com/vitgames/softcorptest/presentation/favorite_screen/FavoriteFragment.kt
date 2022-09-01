package com.vitgames.softcorptest.presentation.favorite_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.databinding.FragmentFavoriteBinding
import com.vitgames.softcorptest.presentation.main_screen.MainViewModel
import com.vitgames.softcorptest.utils.SimpleItemTouchHelperCallback
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by activityViewModels<MainViewModel> { modelFactory }
    private var recycler: RecyclerView? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        (context?.applicationContext as MainApplication).appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = binding.recyclerFavorite
        recycler?.adapter = FavoriteAdapter (
            { item -> viewModel.onStarIconClick(item) },
            { item -> viewModel.onItemRemoved(item) })

        val adapter = (recycler?.adapter as FavoriteAdapter)
        adapter.setData(viewModel.getFavoriteRateData())

        viewModel.favoriteData.observe(viewLifecycleOwner) { newData ->
            adapter.setData(newData)
        }

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter, true)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler)
    }

    override fun onResume() {
        val adapter = (recycler?.adapter as FavoriteAdapter)
        adapter.setData(viewModel.getFavoriteRateData())
        super.onResume()
    }
}