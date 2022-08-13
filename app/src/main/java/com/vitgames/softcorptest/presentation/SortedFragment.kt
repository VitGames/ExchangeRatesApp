package com.vitgames.softcorptest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.databinding.FragmentSortedBinding
import javax.inject.Inject

class SortedFragment : Fragment(R.layout.fragment_sorted) {

    private lateinit var binding: FragmentSortedBinding
    private val viewModel by activityViewModels<SortedViewModel> { modelFactory }
    private var recycler: RecyclerView? = null

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortedBinding.inflate(inflater)
        (context?.applicationContext as MainApplication).appComponent.inject(this)
        return binding.root
    }
}