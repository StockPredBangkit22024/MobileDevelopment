package com.dicoding.stockpred.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.stockpred.R
import com.dicoding.stockpred.data.model.Stock

class StockAdapter(
    private val listStock: ArrayList<Stock>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<StockAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_stock_name)
        val tvCode: TextView = itemView.findViewById(R.id.tv_stock_code)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listStock.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val stock = listStock[position]
        Log.d("StockAdapter", "Logo URL: ${stock.logo}")

        // Load an image using Glide
        Glide.with(holder.itemView.context)
            .load(stock.logo)
            .placeholder(R.drawable.placeholder_logo)
            .error(R.drawable.placeholder_logo)
            .into(holder.imgPhoto)

        holder.tvName.text = stock.name
        holder.tvCode.text = stock.code

        // Send stock code when an item is clicked
        holder.itemView.setOnClickListener {
            onItemClick(stock.code)
        }
    }
}
