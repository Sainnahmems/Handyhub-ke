package com.example.handyhubke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.handyhubke.databinding.ActivityServiceprovidersBinding

class serviceproviders : AppCompatActivity() {
    private lateinit var binding: ActivityServiceprovidersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceprovidersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_worker) as NavHostFragment
        val navController = navHostFragment.navController
        binding.workerBottomNav.setupWithNavController(navController)
    }
}
