package com.example.Shoppies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Shoppies.R
import com.example.Shoppies.architecture.ProductViewModel
import com.example.Shoppies.response.Products
import kotlinx.android.synthetic.main.cart_view.view.*

class CartAdapter(
    val viewModel: ProductViewModel
): RecyclerView.Adapter<CartAdapter.Cartviewholder>() {

    inner class Cartviewholder(item: View): RecyclerView.ViewHolder(item)

    private val differcallback=object: DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,differcallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cartviewholder {
        return Cartviewholder(LayoutInflater.from(parent.context).inflate(R.layout.cart_view,parent,false))
    }

    override fun onBindViewHolder(holder: Cartviewholder, position: Int) {
        val product=differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(product.image).into(ivArtImage)
            tvPrice.text=product.price.toString()
            tvTitle.text=product.title
            tvCurrentAmount.text=product.amt
            btAdd.setOnClickListener {
                var amount=tvCurrentAmount.text.toString().toInt()
                amount++
                product.amt=amount.toString()
                viewModel.upsert(product)
                tvCurrentAmount.text=amount.toString()
                viewModel.increase(product.price!!.toBigDecimal())
            }

            btRemove.setOnClickListener {
                var amount=tvCurrentAmount.text.toString().toInt()
                if(amount>0){
                    amount--
                    product.amt=amount.toString()
                    viewModel.upsert(product)
                    tvCurrentAmount.text=amount.toString()
                    viewModel.decrease(product.price!!.toBigDecimal())
                }
                else{
                    Toast.makeText(context,"Amount already is zero",Toast.LENGTH_SHORT).show()
                }
            }

            btDelete.setOnClickListener {
                val amount=tvCurrentAmount.text.toString().toInt()
                viewModel.deletePrice(amount,product.price!!)
                viewModel.delete(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}