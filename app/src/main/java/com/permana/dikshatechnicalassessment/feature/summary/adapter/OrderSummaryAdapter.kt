package com.permana.dikshatechnicalassessment.feature.summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permana.dikshatechnicalassessment.R
import com.permana.dikshatechnicalassessment.core.data.product.api.model.CartItem
import com.permana.dikshatechnicalassessment.databinding.ItemOrderSummaryBinding

class OrderSummaryAdapter : ListAdapter<CartItem, OrderSummaryAdapter.OrderSummaryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryViewHolder {
        val binding = ItemOrderSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderSummaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrderSummaryViewHolder(private val binding: ItemOrderSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            with(binding) {
                Glide.with(ivSummaryProductImage.context)
                    .load(cartItem.imageUrl)
                    .into(ivSummaryProductImage)
                tvSummaryProductTitle.text = cartItem.title
                tvSummaryProductPrice.text =
                    binding.root.context.getString(R.string.dolar_price, cartItem.price.toString())
                tvSummaryProductQuantity.text = "Qty: ${cartItem.quantity}"
                tvSummaryTotalPriceProduct.text =
                    binding.root.context.getString(R.string.dolar_price, (cartItem.price * cartItem.quantity).toString())
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem == newItem
    }
}
