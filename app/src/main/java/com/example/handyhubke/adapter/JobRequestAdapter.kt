package com.example.handyhubke.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handyhubke.R
import com.example.handyhubke.data.model.JobRequest

class JobRequestAdapter(
    private var jobRequests: List<JobRequest>,
    private val isProvider: Boolean = false,
    private val onAcceptClick: ((JobRequest) -> Unit)? = null,
    private val onDeclineClick: ((JobRequest) -> Unit)? = null,
    private val onJobClick: ((JobRequest) -> Unit)? = null
) : RecyclerView.Adapter<JobRequestAdapter.JobViewHolder>() {

    class JobViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvServiceType: TextView = view.findViewById(R.id.tvServiceType)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvBudget: TextView = view.findViewById(R.id.tvBudget)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val llActions: View = view.findViewById(R.id.llActions)
        val btnAccept: View = view.findViewById(R.id.btnAccept)
        val btnDecline: View = view.findViewById(R.id.btnDecline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_request, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobRequests[position]
        holder.tvServiceType.text = job.serviceType
        holder.tvDescription.text = job.description
        holder.tvBudget.text = "Budget: $${job.budget}"
        holder.tvStatus.text = job.status

        if (isProvider && job.status == "Pending") {
            holder.llActions.visibility = View.VISIBLE
            holder.btnAccept.setOnClickListener { onAcceptClick?.invoke(job) }
            holder.btnDecline.setOnClickListener { onDeclineClick?.invoke(job) }
        } else {
            holder.llActions.visibility = View.GONE
        }

        holder.itemView.setOnClickListener { onJobClick?.invoke(job) }
    }

    override fun getItemCount() = jobRequests.size

    fun updateData(newJobs: List<JobRequest>) {
        this.jobRequests = newJobs
        notifyDataSetChanged()
    }
}
