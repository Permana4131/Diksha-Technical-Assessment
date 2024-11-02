package com.permana.dikshatechnicalassessment.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permana.dikshatechnicalassessment.R
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductItem
import com.permana.dikshatechnicalassessment.databinding.ItemProductBinding

class ProductAdapter(
    private val onItemClick: ((Int) -> Unit),
) : ListAdapter<ProductItem, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductItem) {
            with(binding) {
                tvProductTitle.text = product.title
                tvProductPrice.text = root.context.getString(R.string.dolar_price, product.price.toString())

                Glide.with(ivProductImage.context)
                    .load(product.image)
                    .placeholder(R.drawable.ic_downloading)
                    .into(ivProductImage)

                itemView.setOnClickListener {
                    onItemClick.invoke(product.id)
                }
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem == newItem
        }
    }
}
