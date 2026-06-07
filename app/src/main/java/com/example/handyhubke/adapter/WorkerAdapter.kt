package com.example.handyhubke.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handyhubke.data.model.Worker
import com.example.handyhubke.databinding.ItemWorkerBinding

class WorkerAdapter(
    private var workers: List<Worker>,
    private val onClick: (Worker) -> Unit
) : RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    class WorkerViewHolder(val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val binding = ItemWorkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val worker = workers[position]
        with(holder.binding) {
            tvWorkerName.text = worker.name
            tvWorkerCategory.text = worker.category
            tvWorkerPrice.text = "$${worker.hourlyRate}/hr"
            tvWorkerMetrics.text = "★ ${worker.rating} • ${worker.distance} km away"
            
            root.setOnClickListener { onClick(worker) }
        }
    }

    override fun getItemCount() = workers.size

    fun updateData(newWorkers: List<Worker>) {
        workers = newWorkers
        notifyDataSetChanged()
    }
}