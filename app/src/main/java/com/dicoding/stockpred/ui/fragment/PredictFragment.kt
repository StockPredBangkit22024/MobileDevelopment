package com.dicoding.stockpred.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.stockpred.data.request.PredictRequest
import com.dicoding.stockpred.data.response.PredictResponse
import com.dicoding.stockpred.R
import com.dicoding.stockpred.data.response.StockPrediction
import com.dicoding.stockpred.data.model.PredictResult
import com.dicoding.stockpred.data.response.StockResponseItem
import com.dicoding.stockpred.data.retrofit.ApiConfig
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictFragment : Fragment() {

    private lateinit var stockDetails: Map<String, StockResponseItem>
    private lateinit var progressBar: ProgressBar

    // Variable to store input value
    private var exchangeRate: String? = null
    private var biRate: String? = null
    private var inflationRate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_predict, container, false)

        progressBar = view.findViewById(R.id.progressBar)

        val input1 = view.findViewById<TextInputLayout>(R.id.textInputLayout1).editText
        val input2 = view.findViewById<TextInputLayout>(R.id.textInputLayout2).editText
        val input3 = view.findViewById<TextInputLayout>(R.id.textInputLayout3).editText

        val buttonPredict = view.findViewById<Button>(R.id.buttonPredict)
        val buttonClear = view.findViewById<Button>(R.id.buttonClear)

        // Set input to only allow numbers
        input1?.inputType =
            android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        input2?.inputType =
            android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        input3?.inputType =
            android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL

        // Fetch stock details from the API
        fetchStockDetails()

        // Restore saved state if available
        if (savedInstanceState != null) {
            input1?.setText(savedInstanceState.getString("INPUT_1", ""))
            input2?.setText(savedInstanceState.getString("INPUT_2", ""))
            input3?.setText(savedInstanceState.getString("INPUT_3", ""))
        }

        buttonPredict.setOnClickListener {
            if (TextUtils.isEmpty(input1?.text) ||
                TextUtils.isEmpty(input2?.text) ||
                TextUtils.isEmpty(input3?.text)
            ) {
                Toast.makeText(activity, getString(R.string.input_prompt), Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Store values before making the prediction
                exchangeRate = input1?.text.toString()
                biRate = input2?.text.toString()
                inflationRate = input3?.text.toString()

                val exchangeRateValue = exchangeRate?.toDoubleOrNull()
                val biRateValue = biRate?.toDoubleOrNull()
                val inflationRateValue = inflationRate?.toDoubleOrNull()

                if (exchangeRateValue != null && biRateValue != null && inflationRateValue != null) {
                    // Display loading indicator
                    progressBar.visibility = View.VISIBLE

                    // Fetch prediction data
                    fetchPrediction(exchangeRateValue, biRateValue, inflationRateValue)
                } else {
                    Toast.makeText(activity, getString(R.string.invalid_input), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        buttonClear.setOnClickListener {
            input1?.setText("")
            input2?.setText("")
            input3?.setText("")
        }

        return view
    }

    // Call API to get stock details
    private fun fetchStockDetails() {
        ApiConfig.getApiService().getStocks().enqueue(object : Callback<List<StockResponseItem>> {
            override fun onResponse(
                call: Call<List<StockResponseItem>>,
                response: Response<List<StockResponseItem>>
            ) {
                if (response.isSuccessful) {
                    // Save stock details into a map based on stock code
                    stockDetails = response.body()?.associateBy { it.code } ?: emptyMap()
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.stock_detail_fetch_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<StockResponseItem>>, t: Throwable) {
                Toast.makeText(activity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Call the API to get predictions
    private fun fetchPrediction(exchangeRate: Double, biRate: Double, inflationRate: Double) {
        val request = PredictRequest(exchangeRate, biRate, inflationRate)

        ApiConfig.getApiService().predictStock(request).enqueue(object : Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let { predictResponse ->
                        // Combine the data of name, logo, and predicted price
                        val predictions = predictResponse.prediction.map {
                            val stockDetail = stockDetails[it.key]
                            val predictedPrice =
                                if (it.value < 0) "No Value" else it.value.toString()
                            StockPrediction(
                                name = stockDetail?.name ?: getString(R.string.unknown_name),
                                code = it.key,
                                logo = stockDetail?.logo ?: getString(R.string.unknown_logo_url),
                                predictedPrice = predictedPrice
                            )
                        }
                        // Send the combined data to PredictResult
                        val intent = Intent(activity, PredictResult::class.java).apply {
                            putExtra("PREDICT_RESULT", ArrayList(predictions))
                        }
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.prediction_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(activity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Save instance state to persist input data across configuration changes
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            "INPUT_1",
            view?.findViewById<TextInputLayout>(R.id.textInputLayout1)?.editText?.text.toString()
        )
        outState.putString(
            "INPUT_2",
            view?.findViewById<TextInputLayout>(R.id.textInputLayout2)?.editText?.text.toString()
        )
        outState.putString(
            "INPUT_3",
            view?.findViewById<TextInputLayout>(R.id.textInputLayout3)?.editText?.text.toString()
        )
    }
}
