package com.dicoding.stockpred

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.stockpred.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activeFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Cek koneksi internet
        if (!networkCheck(this)) {
            DialogUtil.showNoInternetDialog(this, layoutInflater)
        }

        // Pulihkan fragment jika ada
        if (savedInstanceState != null) {
            activeFragmentTag = savedInstanceState.getString("ACTIVE_FRAGMENT_TAG")
            val currentFragment = supportFragmentManager.findFragmentByTag(activeFragmentTag)
            currentFragment?.let {
                replaceFragment(it, activeFragmentTag!!)
            }
        } else {
            // Default fragment
            replaceFragment(Home(), "Home")
        }

        // Setup bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home(), "Home")
                R.id.predict -> replaceFragment(Predict(), "Predict")
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Ganti fragment yang ada dengan tag tertentu
        fragmentTransaction.replace(R.id.frame_layout, fragment, tag)
        fragmentTransaction.commit()

        // Simpan tag fragment aktif
        activeFragmentTag = tag
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Simpan fragment aktif
        outState.putString("ACTIVE_FRAGMENT_TAG", activeFragmentTag)
    }
}
