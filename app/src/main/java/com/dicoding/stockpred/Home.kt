package com.dicoding.stockpred

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.stockpred.detail.DetailStockActivity

class Home : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var stockAdapter: StockAdapter
    private lateinit var stockList: ArrayList<Stock>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_stock)

        val stockCodes = resources.getStringArray(R.array.stock_code)
        val stockNames = resources.getStringArray(R.array.stock_name)
        val stockPrices = resources.getStringArray(R.array.stock_prices)

        if (stockCodes.size == stockNames.size && stockCodes.size == stockPrices.size) {
            stockList = ArrayList()
            for (i in stockCodes.indices) {
                stockList.add(
                    Stock(
                        name = stockNames[i],
                        description = stockCodes[i],
                        photo = R.drawable.placeholder_logo,
                        price = stockPrices[i] // Tambahkan harga saham
                    )
                )
            }
        }

        stockAdapter = StockAdapter(stockList) { stock ->
            val intent = Intent(activity, DetailStockActivity::class.java).apply {
                putExtra("EXTRA_STOCK", stock)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = stockAdapter

        return view
    }



}