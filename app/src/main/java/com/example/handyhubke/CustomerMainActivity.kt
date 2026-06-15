package com.example.handyhubke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.handyhubke.databinding.ActivityNavigationFragmentBinding

class CustomerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_customer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.customerBottomNav.setupWithNavController(navController)
    }
}