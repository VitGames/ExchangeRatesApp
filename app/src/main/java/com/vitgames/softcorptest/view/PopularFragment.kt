package com.vitgames.softcorptest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.databinding.FragmentPopularBinding
import com.vitgames.softcorptest.domain.PopularAdapter
import com.vitgames.softcorptest.domain.PopularViewModel
import com.vitgames.softcorptest.domain.SimpleItemTouchHelperCallback
import javax.inject.Inject


class PopularFragment : Fragment(R.layout.fragment_popular) {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var viewModel: PopularViewModel
    private var recycler: RecyclerView? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater)
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = binding.recyclerPopular
        recycler?.adapter = PopularAdapter()
        val adapter = (recycler?.adapter as PopularAdapter)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler)

        viewModel.currentData.observe(viewLifecycleOwner) { newData ->
            adapter.setData(newData)
        }
    }

    private fun initViewModel() {
        (context?.applicationContext as MainApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, modelFactory)[PopularViewModel::class.java]
    }
}