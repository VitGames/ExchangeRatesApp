package com.vitgames.softcorptest.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitgames.softcorptest.api.RateData
import com.vitgames.softcorptest.databinding.RecyclerItemBinding

class PopularAdapter : RecyclerView.Adapter<PopularViewHolder>() {

    private var data = mutableListOf<RateData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: MutableList<RateData>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}

class PopularViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RateData) {
        binding.icon.setImageResource(item.icon)
        binding.rateName.text = item.name
        binding.rateValue.text = item.value
    }
}