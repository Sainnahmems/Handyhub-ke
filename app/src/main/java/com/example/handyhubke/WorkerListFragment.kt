package com.example.handyhubke

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.adapter.WorkerAdapter
import com.example.handyhubke.data.api.RetrofitClient
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.databinding.FragmentWorkerListBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

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
        binding.tvCategoryTitle.text = getString(R.string.category_specialists_format, category)

        binding.rvWorkers.layoutManager = LinearLayoutManager(context)
        fetchWorkers(category)
    }

    private fun fetchWorkers(category: String) {
        binding.progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val categoryQuery = if (category == "All") null else category
                val response = RetrofitClient.apiService.getProviders(categoryQuery)
                if (response.isSuccessful && (response.body() != null)) {
                    val providers = response.body()!!
                    val allWorkers = providers.map { provider ->
                        Worker(
                            id = provider.id,
                            name = provider.name,
                            category = provider.category,
                            rating = provider.rating,
                            hourlyRate = provider.hourlyRate,
                            distance = Random.nextDouble(0.1, 15.0),
                            description = provider.description ?: "Professional service specialist",
                            imageUrl = provider.imageUrl ?: "https://i.pravatar.cc/150?u=${provider.id}",
                        )
                    }

                    binding.rvWorkers.adapter = WorkerAdapter(allWorkers) { worker ->
                        val intent = Intent(context, WorkerProfileActivity::class.java).apply {
                            putExtra("DATA_WORKER", worker)
                        }
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(context, "Error fetching workers", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Connection Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
