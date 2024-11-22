package com.dicoding.stockpred.input

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.stockpred.R
import com.dicoding.stockpred.Stock
import com.dicoding.stockpred.StockAdapter

class PredictResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict_result)

        // Set up the Toolbar with a back button
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)  // Enable the "back" button

        // Handle the back button click
        toolbar.setNavigationOnClickListener {
            onBackPressed()  // Goes back to the previous screen
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Load data from string arrays
        val stockCodes = resources.getStringArray(R.array.stock_code)
        val stockNames = resources.getStringArray(R.array.stock_name)
        val stockPrices = resources.getStringArray(R.array.stock_prices)

        // Prepare list of Stock objects
        val stockList = ArrayList<Stock>()
        for (i in stockCodes.indices) {
            stockList.add(
                Stock(
                    name = stockNames[i],
                    description = stockCodes[i],
                    photo = R.drawable.placeholder_logo,
                    price = stockPrices[i]
                )
            )
        }

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StockAdapter(stockList) { stock ->
            // Handle item click
            showStockDetails(stock)
        }
    }

    private fun showStockDetails(stock: Stock) {
        // Handle stock item click, e.g., show a Toast or navigate to a new screen
    }
}
