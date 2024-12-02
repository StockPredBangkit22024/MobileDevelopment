package com.dicoding.stockpred.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.stockpred.ui.fragment.HomeFragment
import com.dicoding.stockpred.R
import com.dicoding.stockpred.databinding.ActivityMainBinding
import com.dicoding.stockpred.ui.fragment.PredictFragment
import com.dicoding.stockpred.utils.DialogUtil
import com.dicoding.stockpred.utils.NetworkUtil
import com.dicoding.stockpred.utils.networkCheck

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activeFragmentTag: String? = null
    private var isNetworkAvailable: Boolean? = null

    private fun refreshCurrentFragment() {
        val currentFragment = supportFragmentManager.findFragmentByTag(activeFragmentTag)
        currentFragment?.let { replaceFragment(it, activeFragmentTag!!) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Register a listener for internet connection changes
        NetworkUtil.registerNetworkCallback(this, object : NetworkUtil.NetworkStateListener {
            override fun onNetworkAvailable() {
                if (isNetworkAvailable == false || isNetworkAvailable == null) {
                    isNetworkAvailable = true
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.internet_available),
                            Toast.LENGTH_SHORT
                        ).show()
                        refreshCurrentFragment()
                    }
                }
            }

            override fun onNetworkLost() {
                if (isNetworkAvailable == true || isNetworkAvailable == null) {
                    isNetworkAvailable = false
                    runOnUiThread {
                        if (!isFinishing && !isDestroyed) {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.internet_lost),
                                Toast.LENGTH_SHORT
                            ).show()
                            DialogUtil.showNoInternetDialog(this@MainActivity, layoutInflater)
                        }
                    }
                }
            }
        })

        // Check internet connection at the start of the app
        if (!networkCheck(this)) {
            isNetworkAvailable = false
            if (!isFinishing && !isDestroyed) {
                DialogUtil.showNoInternetDialog(this, layoutInflater)
            }
        } else {
            isNetworkAvailable = true
        }

        // Restore fragment if necessary
        if (savedInstanceState != null) {
            activeFragmentTag = savedInstanceState.getString("ACTIVE_FRAGMENT_TAG")
            val currentFragment = supportFragmentManager.findFragmentByTag(activeFragmentTag)
            currentFragment?.let {
                replaceFragment(it, activeFragmentTag!!)
            }
        } else {
            replaceFragment(HomeFragment(), "Home")
        }

        // Setup bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment(), "Home")
                R.id.predict -> replaceFragment(PredictFragment(), "Predict")
                else -> {}
            }
            true
        }
        binding.root.fitsSystemWindows = true
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, tag)
        fragmentTransaction.commit()
        activeFragmentTag = tag
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("ACTIVE_FRAGMENT_TAG", activeFragmentTag)
    }
}
