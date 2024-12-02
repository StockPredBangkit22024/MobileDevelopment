package com.dicoding.stockpred.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.stockpred.R
import com.dicoding.stockpred.data.model.Stock
import com.dicoding.stockpred.data.retrofit.ApiConfig
import com.dicoding.stockpred.data.response.StockResponseItem
import com.dicoding.stockpred.ui.activity.DetailStockActivity
import com.dicoding.stockpred.ui.adapter.StockAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var stockAdapter: StockAdapter
    private lateinit var progressBar: ProgressBar
    private var stockList: ArrayList<Stock> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_stock)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(context)

        stockAdapter = StockAdapter(stockList) { stockCode ->
            val intent = Intent(activity, DetailStockActivity::class.java).apply {
                putExtra("EXTRA_STOCK_CODE", stockCode)
            }
            startActivity(intent)
        }
        recyclerView.adapter = stockAdapter

        fetchStockList()

        return view
    }

    private fun fetchStockList() {
        Log.d("HomeFragment", "Memulai pengambilan data saham...")

        if (stockList.isNotEmpty()) {
            Log.d("HomeFragment", "Data sudah ada, tidak perlu memuat ulang.")
            return
        }

        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        val client = ApiConfig.getApiService().getStocks()
        client.enqueue(object : Callback<List<StockResponseItem>> {
            override fun onResponse(
                call: Call<List<StockResponseItem>>,
                response: Response<List<StockResponseItem>>
            ) {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                if (response.isSuccessful) {
                    val stockItems = response.body()
                    stockItems?.let {
                        stockList.clear()
                        for (item in it) {
                            stockList.add(
                                Stock(
                                    name = item.name,
                                    code = item.code,
                                    logo = item.logo
                                )
                            )
                        }
                        stockAdapter.notifyDataSetChanged()
                    }
                } else {
                    Log.e("HomeFragment", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<StockResponseItem>>, t: Throwable) {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                Log.e("HomeFragment", "API call failed: ${t.message}")
            }
        })
    }

    fun reloadData() {
        fetchStockList()
    }


}

