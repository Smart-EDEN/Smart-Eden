package com.example.smarteden.ui.plantnewplant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarteden.R
import com.example.smarteden.data.Plant
import com.example.smarteden.data.PlantViewModel
import com.example.smarteden.databinding.FragmentPlantNewPlantBinding

class PlantNewPlantFragment : Fragment() {

    //private val firestoreViewModel: FireStoreViewModel by activityViewModels()
    //private val fieldViewModel: FieldViewModel by activityViewModels()
    private val plantViewModel: PlantViewModel by activityViewModels()

    private var _binding: FragmentPlantNewPlantBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //firestoreViewModel.getLiveGreenhouses()
        _binding = FragmentPlantNewPlantBinding.inflate(inflater, container, false)

        val recyclerView = binding.rvPlants


        val plantObserver = Observer<ArrayList<Plant>> { newPlant ->

            recyclerView.adapter = PlantListAdapter(newPlant, this)

        }
        //recyclerView.adapter = AlexaListAdapter(firestoreViewModel.savedEinkaufsliste.value!!)
        plantViewModel.savedPlants.observe(viewLifecycleOwner, plantObserver)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnCustom.setOnClickListener {
            findNavController().navigate(R.id.action_plantNewPlantFragment_to_customPlantFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun clickPlant(plant: Plant) {
        Log.d("TODO", "database delete field infos $plant")
    }
}
