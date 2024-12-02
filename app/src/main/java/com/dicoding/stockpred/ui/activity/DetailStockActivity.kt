package com.dicoding.stockpred.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.stockpred.R
import com.dicoding.stockpred.data.response.StockResponseItem
import com.dicoding.stockpred.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_stock)

        val stockCode = intent.getStringExtra("EXTRA_STOCK_CODE")
        if (stockCode != null) {
            fetchStockDetails(stockCode)
        } else {
            Toast.makeText(this, "Stock code not provided!", Toast.LENGTH_SHORT).show()
            finish()
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun fetchStockDetails(stockCode: String) {
        val apiService = ApiConfig.getApiService()
        val call = apiService.getStocks()

        call.enqueue(object : Callback<List<StockResponseItem>> {
            override fun onResponse(
                call: Call<List<StockResponseItem>>,
                response: Response<List<StockResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val stockList = response.body()
                    val stockDetail = stockList?.find { it.code == stockCode }
                    if (stockDetail != null) {
                        displayStockDetails(stockDetail)
                    } else {
                        Toast.makeText(
                            this@DetailStockActivity,
                            "Stock not found!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.e("DetailStockActivity", "Failed to fetch details: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<StockResponseItem>>, t: Throwable) {
                Log.e("DetailStockActivity", "Error: ${t.message}")
            }
        })
    }

    private fun displayStockDetails(stock: StockResponseItem) {
        // Display data to the UI
        findViewById<TextView>(R.id.tv_stock_code).text = stock.code
        findViewById<TextView>(R.id.tv_stock_name).text = stock.name
        findViewById<TextView>(R.id.tv_stock_sector).text = stock.sector
        findViewById<TextView>(R.id.tv_stock_description).text = stock.description

        Glide.with(this)
            .load(stock.logo)
            .placeholder(R.drawable.placeholder_logo)
            .error(R.drawable.placeholder_logo)
            .into(findViewById(R.id.img_stock_logo))

        findViewById<Button>(R.id.btn_view_company).setOnClickListener {
            val url =
                if (!stock.website.startsWith("http://") && !stock.website.startsWith("https://")) {
                    "http://${stock.website}"
                } else {
                    stock.website
                }

            Log.d("DetailStockActivity", "Opening website: $url")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("DetailStockActivity", "Error opening website: ${e.message}")
                Toast.makeText(this, "Unable to open the website!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}