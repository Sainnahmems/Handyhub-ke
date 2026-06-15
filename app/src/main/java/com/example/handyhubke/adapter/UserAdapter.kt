package com.example.handyhubke.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handyhubke.R
import com.example.handyhubke.data.model.UserResponse

class UserAdapter(private var users: List<UserResponse>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvUserName)
        val tvEmail: TextView = view.findViewById(R.id.tvUserEmail)
        val tvCompany: TextView = view.findViewById(R.id.tvUserCompany)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
        holder.tvCompany.text = user.company.name
    }

    override fun getItemCount() = users.size

    fun updateData(newUsers: List<UserResponse>) {
        users = newUsers
        notifyDataSetChanged()
    }
}
