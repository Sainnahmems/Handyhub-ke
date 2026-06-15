package com.example.handyhubke

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handyhubke.adapter.JobRequestAdapter
import com.example.handyhubke.data.local.DatabaseHelper
import com.example.handyhubke.data.model.JobRequest
import com.example.handyhubke.databinding.ActivityJobRequestsListBinding

class JobRequestsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobRequestsListBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: JobRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobRequestsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        setupRecyclerView()

        binding.fabAddJob.setOnClickListener {
            addNewJobRequest()
        }
    }

    private fun setupRecyclerView() {
        val jobs = dbHelper.getAllJobRequests()
        adapter = JobRequestAdapter(
            jobRequests = jobs,
            onJobClick = { job -> showJobOptions(job) }
        )
        binding.rvJobRequests.layoutManager = LinearLayoutManager(this)
        binding.rvJobRequests.adapter = adapter
    }

    private fun addNewJobRequest() {
        // For demonstration, adding a dummy job request.
        val newJob = JobRequest(
            serviceType = "Plumbing",
            description = "Fixing a leaky sink in the kitchen.",
            budget = 50.0,
            status = "Pending"
        )
        val id = dbHelper.insertJobRequest(newJob)
        if (id != -1L) {
            refreshList()
            Toast.makeText(this, "Job Added Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showJobOptions(job: JobRequest) {
        val options = arrayOf("Update Status to Completed", "Delete Request")
        AlertDialog.Builder(this)
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
        Toast.makeText(this, "Status Updated", Toast.LENGTH_SHORT).show()
    }

    private fun deleteJob(job: JobRequest) {
        job.id?.let {
            dbHelper.deleteJobRequest(it)
            refreshList()
            Toast.makeText(this, "Job Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshList() {
        adapter.updateData(dbHelper.getAllJobRequests())
    }
}
