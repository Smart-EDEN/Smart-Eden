package com.example.smarteden.ui.addgreenhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.smarteden.data.FireStoreViewModel
import com.example.smarteden.databinding.FragmentAddGreenhouseBinding

class AddGreenhouseFragment : Fragment() {

    private val firestoreViewModel: FireStoreViewModel by activityViewModels()
    private var _binding: FragmentAddGreenhouseBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddGreenhouseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            val serialNumber = binding.serialNumber.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            firestoreViewModel.connectGreenhouseWithUser(serialNumber, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
