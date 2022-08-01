package com.vitgames.softcorptest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.databinding.FragmentSortedBinding
import com.vitgames.softcorptest.domain.SortedViewModel
import javax.inject.Inject

class SortedFragment : Fragment(R.layout.fragment_sorted) {

    private lateinit var binding: FragmentSortedBinding
    private lateinit var viewModel: SortedViewModel
    private var recycler: RecyclerView? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortedBinding.inflate(inflater)
        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        (context?.applicationContext as MainApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, modelFactory)[SortedViewModel::class.java]
    }
}