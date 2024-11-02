package com.permana.dikshatechnicalassessment.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permana.dikshatechnicalassessment.R
import com.permana.dikshatechnicalassessment.core.data.product.api.model.CartItem
import com.permana.dikshatechnicalassessment.databinding.ItemCartBinding

class CartAdapter(
    private val onIncreaseQuantity: (CartItem) -> Unit,
    private val onDecreaseQuantity: (CartItem) -> Unit,
    private val onDeleteItem: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem, position: Int) {
            Glide.with(binding.ivProductImage.context)
                .load(cartItem.imageUrl)
                .into(binding.ivProductImage)

            binding.tvProductTitle.text = cartItem.title
            binding.tvProductPrice.text = binding.root.context.getString(R.string.dolar_price, cartItem.price.toString())

            binding.tvQuantity.text = cartItem.quantity.toString()

            binding.btnIncrease.setOnClickListener {
                onIncreaseQuantity(cartItem)
                currentPosition = position
            }

            binding.btnDecrease.setOnClickListener {
                onDecreaseQuantity(cartItem)
                currentPosition = position
            }

            binding.btnDelete.setOnClickListener {
                onDeleteItem(cartItem)
                currentPosition = position
            }
        }
    }

    class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}