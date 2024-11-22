package com.dicoding.stockpred

import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.stockpred.databinding.CustomAlertDialogBinding

object DialogUtil {

    fun showNoInternetDialog(activity: AppCompatActivity, layoutInflater: LayoutInflater) {
        val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(activity)
        dialog.setContentView(dialogBinding.root)

        // Menonaktifkan penutupan dialog dengan mengetuk di luar
        dialog.setCancelable(false)

        // Mengambil referensi tombol
        val btnRefresh = dialogBinding.btnRefresh
        val btnClose = dialogBinding.btnClose

        // Menetapkan listener untuk tombol Refresh
        btnRefresh.setOnClickListener {
            // Logika untuk menyegarkan koneksi
            if (networkCheck(activity)) {
                Toast.makeText(activity, "Koneksi berhasil!", Toast.LENGTH_SHORT).show()
                dialog.dismiss() // Menutup dialog jika koneksi berhasil
            } else {
                Toast.makeText(activity, "Koneksi gagal. Coba lagi!", Toast.LENGTH_SHORT).show()
            }
        }

        // Menetapkan listener untuk tombol Close
        btnClose.setOnClickListener {
            dialog.dismiss() // Menutup dialog
            activity.finish() // Menutup aplikasi
        }

        // Menampilkan dialog
        dialog.show()
    }
}