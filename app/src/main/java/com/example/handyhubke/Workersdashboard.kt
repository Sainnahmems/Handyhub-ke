package com.example.handyhubke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.handyhubke.databinding.ActivityWorkersdashboardBinding

class Workersdashboard : AppCompatActivity() {
    private lateinit var binding: ActivityWorkersdashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkersdashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup RecyclerView or other logic here
    }
}
