package com.example.handyhubke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.adapter.JobRequestAdapter
import com.example.handyhubke.data.local.DatabaseHelper
import com.example.handyhubke.data.model.JobRequest
import com.example.handyhubke.databinding.FragmentWorkerDashboardBinding

class WorkerDashboardFragment : Fragment() {
    private var _binding: FragmentWorkerDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: JobRequestAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkerDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val jobs = dbHelper.getAllJobRequests()
        adapter = JobRequestAdapter(
            jobRequests = jobs,
            isProvider = true,
            onAcceptClick = { job -> updateJobStatus(job, "Accepted") },
            onDeclineClick = { job -> updateJobStatus(job, "Declined") }
        )
        binding.rvDashboardJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDashboardJobs.adapter = adapter
    }

    private fun updateJobStatus(job: JobRequest, newStatus: String) {
        val updatedJob = job.copy(status = newStatus)
        dbHelper.updateJobRequest(updatedJob)
        refreshList()
    }

    private fun refreshList() {
        adapter.updateData(dbHelper.getAllJobRequests())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
