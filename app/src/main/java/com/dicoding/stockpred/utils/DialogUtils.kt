package com.dicoding.stockpred.utils

import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.stockpred.R
import com.dicoding.stockpred.databinding.CustomAlertDialogBinding
import com.dicoding.stockpred.ui.fragment.HomeFragment

object DialogUtil {

    fun showNoInternetDialog(activity: AppCompatActivity, layoutInflater: LayoutInflater) {
        val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(activity)
        dialog.setContentView(dialogBinding.root)

        // Disable closing the dialog by tapping outside
        dialog.setCancelable(false)

        val btnRefresh = dialogBinding.btnRefresh
        val btnClose = dialogBinding.btnClose

        // Set listener for the Refresh button
        btnRefresh.setOnClickListener {
            // Logic to refresh the connection
            if (networkCheck(activity)) {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.connection_successful),
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()

                // Reload the fragment
                val currentFragment =
                    activity.supportFragmentManager.findFragmentById(R.id.frame_layout)
                if (currentFragment is HomeFragment) {
                    currentFragment.reloadData()
                }
            } else {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.connection_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
            activity.finish()
        }
        dialog.show()
    }
}
