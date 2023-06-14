package com.example.smarteden.ui.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.smarteden.data.FieldViewModel
import com.example.smarteden.databinding.FragmentControlBinding


class ControlFragment : Fragment() {

    private val fieldViewModel: FieldViewModel by activityViewModels()

    private var _binding: FragmentControlBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //firestoreViewModel.getLiveGreenhouses()
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fieldViewModel.currentGreenhouse.observe(viewLifecycleOwner){ greenhouse ->
            binding.rolloHeader.text = "Rollo ${if(greenhouse.rollo == "open") "offen" else "geschlossen"}"
            binding.windowHeader.text = "Fenster ${if(greenhouse.window == "open") "offen" else "geschlossen"}"
            binding.boostHeader.text = "Boost ${if (greenhouse.boost) "an" else "aus"}"
        }

        binding.rolloUp.setOnClickListener { fieldViewModel.sendRolloCommand("up") }
        binding.rolloDown.setOnClickListener { fieldViewModel.sendRolloCommand("down") }
        binding.openWindow.setOnClickListener { fieldViewModel.sendWindowCommand("open") }
        binding.closeWindow.setOnClickListener { fieldViewModel.sendWindowCommand("closed") }
        binding.boostOn.setOnClickListener { fieldViewModel.sendBoostCommand("on") }
        binding.boostOff.setOnClickListener { fieldViewModel.sendBoostCommand("off") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
