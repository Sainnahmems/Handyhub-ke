package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.adapter.WorkerAdapter
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.databinding.FragmentWorkerListBinding

class WorkerListFragment : Fragment() {
    private var _binding: FragmentWorkerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString("category") ?: "All"
        binding.tvCategoryTitle.text = "$category Specialists"
        
        val allWorkers = listOf(
            Worker("1", "John Doe", "Plumbing", 4.8, 25.0, 1.2, "Expert in leak repairs and pipe installations.", ""),
            Worker("2", "Jane Smith", "Cleaning", 4.9, 15.0, 2.5, "Deep cleaning specialist for homes and offices.", ""),
            Worker("3", "Mike Ross", "Electrician", 4.7, 30.0, 0.8, "Licensed electrician for wiring and repairs.", ""),
            Worker("4", "Rachel Zane", "Painting", 4.6, 20.0, 3.1, "Interior and exterior professional painting.", ""),
            Worker("5", "Harvey Specter", "Plumbing", 5.0, 50.0, 5.0, "High-end plumbing solutions and consultation.", ""),
            Worker("6", "Louis Litt", "Cleaning", 4.5, 12.0, 1.5, "Quick and efficient cleaning services.", ""),
            Worker("7", "Donna Paulsen", "Electrician", 5.0, 40.0, 2.2, "Smart home setup and electrical diagnostics.", ""),
            Worker("8", "Jessica Pearson", "Painting", 4.9, 35.0, 4.0, "Luxury finishing and decorative painting.", "")
        )

        val filteredWorkers = if (category == "All") {
            allWorkers
        } else {
            allWorkers.filter { it.category.equals(category, ignoreCase = true) }
        }

        binding.rvWorkers.layoutManager = LinearLayoutManager(context)
        binding.rvWorkers.adapter = WorkerAdapter(filteredWorkers) { worker ->
            val intent = Intent(context, WorkerProfileActivity::class.java).apply {
                putExtra("DATA_WORKER", worker)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}