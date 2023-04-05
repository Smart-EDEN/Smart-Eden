package com.example.smarteden.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarteden.R
import com.example.smarteden.data.FieldViewModel
import com.example.smarteden.data.FireStoreViewModel
import com.example.smarteden.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    private val firestoreViewModel: FireStoreViewModel by activityViewModels()
    private val fieldViewModel: FieldViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //firestoreViewModel.getLiveGreenhouses()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //firestoreViewModel.getLiveGreenhouses()
        val recyclerView = binding.greenhouseRv
/*
        val greenhousesHome = firestoreViewModel.getLiveGreenhouses()

        greenhousesHome.observe(viewLifecycleOwner) { greenhouses ->
            recyclerView.adapter = GreenhouseAdapter(greenhouses, this)
        }*/
        firestoreViewModel.greenhouses.observe(viewLifecycleOwner) { greenhouses ->
            Log.d("Bene", "Observe")
            recyclerView.adapter = GreenhouseAdapter(greenhouses, this)
        }

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.addGreenhouse.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_addGreenhouseFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToFieldOverview(greenhouseId: String) {
        fieldViewModel.setNewSerialNumber(greenhouseId)
        findNavController().navigate(R.id.action_HomeFragment_to_fieldoverviewFragment)
    }
}
