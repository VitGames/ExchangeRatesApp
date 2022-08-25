package com.vitgames.softcorptest.presentation.favorite_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.data.api.RatePresentationModel
import com.vitgames.softcorptest.databinding.RecyclerItemBinding
import com.vitgames.softcorptest.utils.ItemTouchHelperAdapter
import java.util.*

class FavoriteAdapter(private val clickListener: (RatePresentationModel) -> Unit) :
    RecyclerView.Adapter<FavoriteViewHolder>(),
    ItemTouchHelperAdapter {

    private var data = mutableListOf<RatePresentationModel>()

    fun setData(newData: List<RatePresentationModel>) {
        data.clear()
        data.addAll(newData as MutableList<RatePresentationModel>)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item) { clickListener(item) }
    }

    override fun getItemCount(): Int = data.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}

class FavoriteViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RatePresentationModel, clickListener: (RatePresentationModel) -> Unit) {
        val starDrawable = getDefaultStarIcon(item)

        binding.apply {
            icon.setImageResource(item.icon)
            rateName.text = item.name
            rateValue.text = item.value
            star.apply {
                setImageResource(starDrawable)
                setOnClickListener {
                    clickListener(item)
                    animate().apply {
                        scaleXBy(-.5f)
                        scaleYBy(-.5f)
                    }.withEndAction {
                        star.animate().apply {
                            scaleXBy(.5f)
                            scaleYBy(.5f)
                        }
                        star.setImageResource(
                            getStarIconAfterClick(item)
                        )
                    }

                }
            }
        }
    }

    private fun getDefaultStarIcon(item: RatePresentationModel) =
        if (item.isFavorite) {
            R.drawable.ic_star_clicked
        } else R.drawable.ic_star_non_clicked

    private fun getStarIconAfterClick(item: RatePresentationModel) =
        if (item.isFavorite) {
            R.drawable.ic_star_non_clicked
        } else {
            R.drawable.ic_star_clicked
        }
}