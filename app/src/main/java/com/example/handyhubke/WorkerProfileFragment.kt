package com.example.handyhubke

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.handyhubke.databinding.FragmentWorkerProfileBinding

class WorkerProfileFragment : Fragment() {
    private var _binding: FragmentWorkerProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdateWorkerProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Worker professional profile saved", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            val sharedPrefs = requireContext().getSharedPreferences("HandyHubPrefs", Context.MODE_PRIVATE)
            sharedPrefs.edit { clear() }

            Toast.makeText(requireContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
