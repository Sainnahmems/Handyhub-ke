package com.example.handyhubke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.adapter.JobRequestAdapter
import com.example.handyhubke.data.local.DatabaseHelper
import com.example.handyhubke.data.model.JobRequest
import com.example.handyhubke.databinding.FragmentSavedRequestsBinding

class SavedRequestsFragment : Fragment() {

    private var _binding: FragmentSavedRequestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: JobRequestAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSavedRequestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        setupRecyclerView()

        binding.fabAddJob.setOnClickListener {
            addNewJobRequest()
        }

        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { performSearch(it) }
                return true
            }
        })
    }

    private fun performSearch(query: String) {
        val filteredList = if (query.isEmpty()) {
            dbHelper.getAllJobRequests()
        } else {
            dbHelper.searchJobRequests(query)
        }
        adapter.updateData(filteredList)
    }

    private fun setupRecyclerView() {
        val jobs = dbHelper.getAllJobRequests()
        adapter = JobRequestAdapter(
            jobRequests = jobs,
            onJobClick = { job -> showJobOptions(job) }
        )
        binding.rvJobRequests.layoutManager = LinearLayoutManager(context)
        binding.rvJobRequests.adapter = adapter
    }

    private fun addNewJobRequest() {
        val newJob = JobRequest(
            serviceType = "Plumbing",
            description = "Fixing a leaky sink in the kitchen.",
            budget = 50.0,
            status = "Pending"
        )
        val id = dbHelper.insertJobRequest(newJob)
        if (id != -1L) {
            refreshList()
            Toast.makeText(context, "Job Added Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showJobOptions(job: JobRequest) {
        val options = arrayOf("Update Status to Completed", "Delete Request")
        AlertDialog.Builder(requireContext())
            .setTitle("Job Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> updateJobStatus(job)
                    1 -> deleteJob(job)
                }
            }
            .show()
    }

    private fun updateJobStatus(job: JobRequest) {
        val updatedJob = job.copy(status = "Completed")
        dbHelper.updateJobRequest(updatedJob)
        refreshList()
        Toast.makeText(context, "Status Updated", Toast.LENGTH_SHORT).show()
    }

    private fun deleteJob(job: JobRequest) {
        job.id?.let {
            dbHelper.deleteJobRequest(it)
            refreshList()
            Toast.makeText(context, "Job Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshList() {
        adapter.updateData(dbHelper.getAllJobRequests())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
