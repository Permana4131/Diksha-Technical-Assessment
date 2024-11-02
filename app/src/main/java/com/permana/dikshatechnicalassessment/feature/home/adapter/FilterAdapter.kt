package com.permana.dikshatechnicalassessment.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.permana.dikshatechnicalassessment.R
import com.permana.dikshatechnicalassessment.databinding.ItemFilterBinding
import com.permana.dikshatechnicalassessment.feature.home.helper.ItemFilter

class FilterAdapter (
    private val onItemClick: ((ItemFilter) -> Unit),
): ListAdapter<ItemFilter, FilterAdapter.ViewHolder>(
FilterDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder =
        ViewHolder(ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(
        private val binding: ItemFilterBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: ItemFilter,
        ){
            val context = binding.root.context
            binding.tvFilter.text = item.label

            with(binding){
                llParent.background = ContextCompat.getDrawable(context, if (item.isActive) R.drawable.bg_chip_filter_active else R.drawable.bg_chip_filter)
                tvFilter.setTextColor(ContextCompat.getColor(context, if (item.isActive) R.color.teal_700 else R.color.black))
            }

            binding.llParent.setOnClickListener {
                onItemClick.invoke(item)
            }

        }
    }

}
