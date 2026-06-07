package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val dummyWorkers = listOf(
            Worker("1", "John Doe", "Plumbing", 4.8, 25.0, 1.2, "Expert Plumber", ""),
            Worker("2", "Jane Smith", "Cleaning", 4.9, 15.0, 2.5, "Eco Cleaner", "")
        )

        binding.rvFeaturedWorkers.layoutManager = LinearLayoutManager(context)
        binding.rvFeaturedWorkers.adapter = WorkerAdapter(dummyWorkers) { worker ->
            val intent = Intent(context, WorkerProfileActivity::class.java).apply {
                putExtra("DATA_WORKER", worker)
            }
            startActivity(intent)
        }

        binding.catPlumbing.setOnClickListener { navigateToCategory("Plumbing") }
        binding.catElectrical.setOnClickListener { navigateToCategory("Electrician") }
        binding.catCleaning.setOnClickListener { navigateToCategory("Cleaning") }
        binding.catPainting.setOnClickListener { navigateToCategory("Painting") }
    }

    private fun navigateToCategory(category: String) {
        val bundle = Bundle().apply { putString("category", category) }
        try {
            findNavController().navigate(R.id.action_home_to_workerList, bundle)
        } catch (e: Exception) {
            // Log or handle error
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}