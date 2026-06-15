package com.example.handyhubke

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.data.api.RetrofitClient
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.databinding.FragmentHomeBinding
import com.example.handyhubke.adapter.WorkerAdapter
import com.example.handyhubke.data.local.PreferencesManager
import com.example.handyhubke.data.repository.FirebaseRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferencesManager: PreferencesManager
    private val firebaseRepository = FirebaseRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        preferencesManager = PreferencesManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve username and role from PreferencesManager
        val username = preferencesManager.getAuthToken() ?: "User" // Simplified for demo
        val role = preferencesManager.getUserRole()
        
        binding.tvWelcomeMessage.text = getString(R.string.welcome_user, username)
        
        // Example of conditional UI based on role stored locally
        if (role == PreferencesManager.ROLE_PROFESSIONAL) {
            // Show professional dashboard features if needed
        }

        // Quick Navigation Buttons
        binding.cardBookService.setOnClickListener {
            navigateToCategory("All")
        }

        binding.cardSavedRequests.setOnClickListener {
            findNavController().navigate(R.id.nav_saved_requests)
        }

        binding.cardBrowsePros.setOnClickListener {
            navigateToCategory("All")
        }

        binding.cardPublicRecords.setOnClickListener {
            startActivity(Intent(requireContext(), UserListActivity::class.java))
        }

        // Category Clicks
        binding.catPlumbing.setOnClickListener { navigateToCategory("Plumbing") }
        binding.catElectrical.setOnClickListener { navigateToCategory("Electrician") }
        binding.catCleaning.setOnClickListener { navigateToCategory("Cleaning") }
        binding.catPainting.setOnClickListener { navigateToCategory("Painting") }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvFeaturedWorkers.layoutManager = LinearLayoutManager(context)
        
        // Fetch featured workers from Firebase Firestore (Remote Layer)
        binding.homeProgressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Example: Fetch "Plumbing" specialists
                val result = firebaseRepository.getServiceProviders("Plumbing")
                if (result.isSuccess) {
                    val providers = result.getOrNull() ?: emptyList()
                    val workers = providers.take(5).map { profile ->
                        Worker(
                            id = profile.uid,
                            name = profile.fullName,
                            category = profile.role,
                            rating = profile.rating,
                            hourlyRate = profile.hourlyRate,
                            distance = Random.nextDouble(0.5, 10.0),
                            description = "Certified professional",
                            imageUrl = profile.profileImageUrl.ifEmpty { "https://i.pravatar.cc/150?u=${profile.uid}" },
                        )
                    }
                    binding.rvFeaturedWorkers.adapter = WorkerAdapter(workers) { worker ->
                        val intent = Intent(context, WorkerProfileActivity::class.java).apply {
                            putExtra("DATA_WORKER", worker)
                        }
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(context, "Failed to load specialists from Cloud", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Cloud Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.homeProgressBar.visibility = View.GONE
            }
        }
    }

    private fun navigateToCategory(category: String) {
        val bundle = Bundle().apply { putString("category", category) }
        try {
            findNavController().navigate(R.id.action_home_to_workerList, bundle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
