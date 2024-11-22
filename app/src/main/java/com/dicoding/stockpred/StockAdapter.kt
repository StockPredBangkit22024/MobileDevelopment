package com.dicoding.stockpred

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StockAdapter(
    private val listStock: ArrayList<Stock>,
    private val onItemClick: (Stock) -> Unit
) : RecyclerView.Adapter<StockAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_stock_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_stock_code)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listStock.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val stock = listStock[position]
        holder.imgPhoto.setImageResource(stock.photo)
        holder.tvName.text = stock.name
        holder.tvDescription.text = stock.description

        // Harga tidak ditampilkan
        holder.itemView.findViewById<TextView>(R.id.tv_stock_price).text = "Rp ${stock.price}"

        holder.itemView.setOnClickListener {
            onItemClick(stock)
        }
    }

}