package com.vitgames.softcorptest.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.data.api.RatePresentationModel
import com.vitgames.softcorptest.databinding.RecyclerItemBinding
import java.util.*

class PopularAdapter(private val clickListener: (RatePresentationModel) -> Unit) :
    RecyclerView.Adapter<PopularViewHolder>(),
    ItemTouchHelperAdapter {

    private var data = mutableListOf<RatePresentationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item) { clickListener(item) }
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: List<RatePresentationModel>) {
        data.clear()
        data.addAll(newData as MutableList<RatePresentationModel>)
        notifyDataSetChanged()
    }

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

class PopularViewHolder(private val binding: RecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

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

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}

class SimpleItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean = true

    override fun isItemViewSwipeEnabled(): Boolean = false

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {}
}