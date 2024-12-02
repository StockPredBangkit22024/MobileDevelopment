package com.dicoding.stockpred.data.model

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.stockpred.R
import com.dicoding.stockpred.data.response.StockPrediction
import com.dicoding.stockpred.ui.activity.DetailStockActivity
import com.dicoding.stockpred.ui.adapter.PredictResultAdapter

class PredictResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict_result)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Receive prediction data from PredictFragment
        val predictions = intent.getParcelableArrayListExtra<StockPrediction>("PREDICT_RESULT")

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (predictions != null) {
            recyclerView.adapter = PredictResultAdapter(predictions) { stockCode ->
                // Intent ke DetailStockActivity
                val intent = Intent(this, DetailStockActivity::class.java)
                intent.putExtra("EXTRA_STOCK_CODE", stockCode)
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, getString(R.string.no_prediction_result), Toast.LENGTH_SHORT)
                .show()
        }

    }
}

