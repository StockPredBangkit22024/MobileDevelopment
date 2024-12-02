package com.dicoding.stockpred.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.stockpred.R
import com.dicoding.stockpred.data.response.StockPrediction

class PredictResultAdapter(
    private val predictions: List<StockPrediction>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<PredictResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPhoto: ImageView = view.findViewById(R.id.img_item_photo)
        val tvCode: TextView = view.findViewById(R.id.tv_stock_code)
        val tvName: TextView = view.findViewById(R.id.tv_stock_name)
        val tvPrice: TextView = view.findViewById(R.id.tv_stock_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stock_price, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prediction = predictions[position]

        holder.tvCode.text = prediction.code
        holder.tvName.text = prediction.name
        holder.tvPrice.text = "${prediction.predictedPrice}"

        Glide.with(holder.itemView.context)
            .load(prediction.logo)
            .placeholder(R.drawable.placeholder_logo)
            .error(R.drawable.placeholder_logo)
            .into(holder.imgPhoto)

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClick(prediction.code)
        }
    }

    override fun getItemCount(): Int = predictions.size
}
