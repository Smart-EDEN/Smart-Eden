package com.example.smarteden.ui.plantnewplant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smarteden.R
import com.example.smarteden.data.FieldViewModel
import com.example.smarteden.data.Plant
import com.example.smarteden.data.PlantViewModel
import com.example.smarteden.databinding.FragmentCustomPlantBinding
import com.example.smarteden.generalfunctions.datemanipulation.convertLongToTime
import com.google.android.material.datepicker.MaterialDatePicker


class CustomPlantFragment : Fragment() {

    //private val firestoreViewModel: FireStoreViewModel by activityViewModels()
    private val fieldViewModel: FieldViewModel by activityViewModels()
    private val plantViewModel: PlantViewModel by activityViewModels()

    private var _binding: FragmentCustomPlantBinding? = null
    private var dateLong = 0L

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

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        binding.btnPickDate.setOnClickListener {
            datePicker.show(parentFragmentManager, "Bier")
        }

        datePicker.addOnPositiveButtonClickListener {newTime ->
            Log.d("Bier", newTime.toString())
            dateLong = newTime
            val date = convertLongToTime(newTime)
            binding.customPlantDate.text = "Ausgewähltes Datum: $date"
            // Respond to positive button click.
        }
        datePicker.addOnNegativeButtonClickListener {
            // Respond to negative button click.
        }
        datePicker.addOnCancelListener {
            // Respond to cancel button click.
        }
        datePicker.addOnDismissListener {
            // Respond to dismiss events.
        }

        binding.btnSend.setOnClickListener {

            val plant = getPlantData()
            fieldViewModel.plantNewPlantInField(plant, dateLong)
            plantViewModel.saveNewPlant(plant)
            findNavController().navigate(R.id.action_customPlantFragment_to_fieldoverviewFragment)
        }
    }

    private fun getPlantData(): Plant {
        val name = binding.customName.editText?.text.toString()
        val waterAmountId = binding.amountWatering.checkedRadioButtonId
        var waterAmount = MUCH
        when (waterAmountId) {
            binding.radioButton1.id -> {
                waterAmount = MUCH
            }
            binding.radioButton2.id -> {
                waterAmount = ZW_MUCH_MIDDLE
            }
            binding.radioButton3.id -> {
                waterAmount = MIDDLE
            }
            binding.radioButton4.id -> {
                waterAmount = ZW_MIDDLE_LESS
            }
            binding.radioButton5.id -> {
                waterAmount = LESS
            }
        }
        val wateringFrequencyId = binding.frequencyWatering.checkedRadioButtonId
        var wateringFrequency = OFTEN
        when (wateringFrequencyId) {
            binding.radioButtonOften.id -> {
                wateringFrequency = OFTEN
            }
            binding.radioButtonMiddle.id -> {
                wateringFrequency = MIDDLE
            }
            binding.radioButtonRarely.id -> {
                wateringFrequency = RARELY
            }
        }
        val ripeDuration = binding.customDuration.editText?.text.toString().toInt()
        val plantMonths = binding.customPlantMonths.editText?.text.toString()
        val harvestMonths = binding.customHarvestMonths.editText?.text.toString()
        val generalInfo = binding.customGeneralInfo.editText?.text.toString()

        return Plant(
            id = "1",
            name,
            ripeDuration,
            plantMonths,
            harvestMonths,
            generalInfo,
            waterAmount,
            wateringFrequency
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object{
        private const val MUCH = 5
        private const val ZW_MUCH_MIDDLE = 4
        private const val MIDDLE = 3
        private const val ZW_MIDDLE_LESS = 2
        private const val LESS = 1

        private const val OFTEN = 5
        private const val RARELY = 1
    }
}
