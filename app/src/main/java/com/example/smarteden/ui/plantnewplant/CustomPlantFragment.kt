package com.example.smarteden.ui.plantnewplant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smarteden.databinding.FragmentCustomPlantBinding


class CustomPlantFragment : Fragment() {

    //private val firestoreViewModel: FireStoreViewModel by activityViewModels()
    //private val fieldViewModel: FieldViewModel by activityViewModels()

    private var _binding: FragmentCustomPlantBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //firestoreViewModel.getLiveGreenhouses()
        _binding = FragmentCustomPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
