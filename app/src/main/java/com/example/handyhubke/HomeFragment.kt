package com.example.handyhubke

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.databinding.FragmentHomeBinding
import com.example.handyhubke.adapter.WorkerAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve username from SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("HandyHubPrefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "User")
        binding.tvWelcomeMessage.text = getString(R.string.welcome_user, username)

        // Quick Navigation Buttons
        binding.cardBookService.setOnClickListener {
            // Placeholder: Navigate to a default category or search
            navigateToCategory("All")
        }

        binding.cardSavedRequests.setOnClickListener {
            // Placeholder: Navigate to Local CRUD Screen (Saved Requests)
            // findNavController().navigate(R.id.nav_saved_requests) 
            Toast.makeText(context, "Navigating to Saved Requests...", Toast.LENGTH_SHORT).show()
        }

        binding.cardBrowsePros.setOnClickListener {
            navigateToCategory("All")
        }

        // Category Clicks
        binding.catPlumbing.setOnClickListener { navigateToCategory("Plumbing") }
        binding.catElectrical.setOnClickListener { navigateToCategory("Electrician") }
        binding.catCleaning.setOnClickListener { navigateToCategory("Cleaning") }
        binding.catPainting.setOnClickListener { navigateToCategory("Painting") }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val dummyWorkers = listOf(
            Worker("1", "John Doe", "Plumbing", 4.8, 25.0, 1.2, "Expert Plumber", ""),
            Worker("2", "Jane Smith", "Cleaning", 4.9, 15.0, 2.5, "Eco Cleaner", ""),
            Worker("3", "Mike Ross", "Electrician", 4.7, 30.0, 0.8, "Licensed electrician", ""),
        )

        binding.rvFeaturedWorkers.layoutManager = LinearLayoutManager(context)
        binding.rvFeaturedWorkers.adapter = WorkerAdapter(dummyWorkers) { worker ->
            val intent = Intent(context, WorkerProfileActivity::class.java).apply {
                putExtra("DATA_WORKER", worker)
            }
            startActivity(intent)
        }
    }

    private fun navigateToCategory(category: String) {
        val bundle = Bundle().apply { putString("category", category) }
        try {
            findNavController().navigate(R.id.action_home_to_workerList, bundle)
        } catch (e: Exception) {
            // Handle navigation error
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
