package com.example.smarteden.ui.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

            if(greenhouse.manualMode && !greenhouse.boost) {
                binding.manualMode.isChecked = true
                makeComponentsVisible()
            } else {
                binding.manualMode.isChecked = false
                makeComponentsGone()
            }
        }

        binding.rolloUp.setOnClickListener { fieldViewModel.sendRolloCommand("up") }
        binding.rolloDown.setOnClickListener { fieldViewModel.sendRolloCommand("down") }
        binding.openWindow.setOnClickListener { fieldViewModel.sendWindowCommand("open") }
        binding.closeWindow.setOnClickListener { fieldViewModel.sendWindowCommand("closed") }
        binding.boostOn.setOnClickListener { fieldViewModel.sendBoostCommand(true) }
        binding.boostOff.setOnClickListener { fieldViewModel.sendBoostCommand(false) }
        binding.manualMode.setOnCheckedChangeListener { button, isChecked ->
            if(fieldViewModel.currentGreenhouse.value!!.boost) {
                Toast.makeText(
                    context,
                    "Manueller modus wegen Boost nicht möglich!",
                    Toast.LENGTH_LONG
                ).show()
                button.isChecked = false
            } else
                fieldViewModel.sendManualMode(isChecked)
        }
    }

    private fun makeComponentsVisible(){
        binding.rolloUp.visibility = View.VISIBLE
        binding.rolloDown.visibility = View.VISIBLE
        binding.rolloHeader.visibility = View.VISIBLE

        binding.windowHeader.visibility = View.VISIBLE
        binding.openWindow.visibility = View.VISIBLE
        binding.closeWindow.visibility = View.VISIBLE
    }

    private fun makeComponentsGone() {
        binding.rolloUp.visibility = View.GONE
        binding.rolloDown.visibility = View.GONE
        binding.rolloHeader.visibility = View.GONE

        binding.windowHeader.visibility = View.GONE
        binding.openWindow.visibility = View.GONE
        binding.closeWindow.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
