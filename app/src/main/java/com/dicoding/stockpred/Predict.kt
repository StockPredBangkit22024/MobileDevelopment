package com.dicoding.stockpred

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.stockpred.input.PredictResult
import com.google.android.material.textfield.TextInputLayout

class Predict : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_predict, container, false)

        // Set click listener for the "Input" button
        view.findViewById<Button>(R.id.buttonPredict).setOnClickListener {
            // Mengakses TextInputEditText melalui TextInputLayout
            val input1 = view.findViewById<TextInputLayout>(R.id.textInputLayout1).editText
            val input2 = view.findViewById<TextInputLayout>(R.id.textInputLayout2).editText
            val input3 = view.findViewById<TextInputLayout>(R.id.textInputLayout3).editText

            // Validasi input, jika kosong tampilkan pemberitahuan
            if (TextUtils.isEmpty(input1?.text) ||
                TextUtils.isEmpty(input2?.text) ||
                TextUtils.isEmpty(input3?.text)
            ) {
                // Tampilkan pesan pemberitahuan
                Toast.makeText(activity, "Input terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
                // Jika semua input sudah diisi, navigasi ke PredictResultActivity
                val intent = Intent(activity, PredictResult::class.java)
                startActivity(intent)
            }
        }

        // Set click listener for the "Clear" button
        view.findViewById<Button>(R.id.buttonClear).setOnClickListener {
            // Mengakses TextInputEditText melalui TextInputLayout
            val input1 = view.findViewById<TextInputLayout>(R.id.textInputLayout1).editText
            val input2 = view.findViewById<TextInputLayout>(R.id.textInputLayout2).editText
            val input3 = view.findViewById<TextInputLayout>(R.id.textInputLayout3).editText

            // Menghapus teks dari semua input
            input1?.setText("")  // Clear input 1
            input2?.setText("")  // Clear input 2
            input3?.setText("")  // Clear input 3
        }

        return view
    }
}
