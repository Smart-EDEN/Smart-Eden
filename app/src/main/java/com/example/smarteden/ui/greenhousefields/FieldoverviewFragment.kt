package com.example.smarteden.ui.greenhousefields

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarteden.R
import com.example.smarteden.data.FieldViewModel
import com.example.smarteden.data.LivePictureViewModel

class FieldoverviewFragment : Fragment(
    ) {
    private val fieldViewModel: FieldViewModel by activityViewModels()
    private val livePictureViewModel: LivePictureViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    //private lateinit var viewModel: FieldViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fieldoverview, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_fields)
        recyclerView.layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMN)//LinearLayoutManager(requireContext())

        fieldViewModel.fields.observe(viewLifecycleOwner){ fields ->
            recyclerView.adapter = RecyclerViewAdapterFieldOverview(fields,this)
        }

        val tvTemperature: TextView = view.findViewById(R.id.temperature)
        val tvHumidity: TextView = view.findViewById(R.id.humidity)

        fieldViewModel.currentGreenhouse.observe(viewLifecycleOwner) { currentGreenhouse ->
            tvTemperature.text = "Temperatur: ${currentGreenhouse.temperature}°C"
            tvHumidity.text = "Luftfeuchtigkeit: ${currentGreenhouse.humidity}%"
        }

        val btnShareGreenhouse: Button = view.findViewById(R.id.btn_share_greenhouse)
        btnShareGreenhouse.setOnClickListener {
            findNavController().navigate(R.id.action_fieldoverviewFragment_to_shareGreenhouseFragment)
        }

        val btnLivePic: Button = view.findViewById(R.id.btn_camera)
        btnLivePic.setOnClickListener {
            findNavController().navigate(R.id.action_fieldoverviewFragment_to_livePictureFragment)
        }

        val btnControlGreenhouse:  Button = view.findViewById(R.id.btn_control_greenhouse)
        btnControlGreenhouse.setOnClickListener {
            findNavController().navigate(R.id.action_fieldoverviewFragment_to_controlFragment)
        }
        livePictureViewModel.changeSerialNumber(fieldViewModel.serialNumber)
        return view
    }

    fun navigateToFragment(id: String) {
        fieldViewModel.getFieldWithId(id)
        findNavController().navigate(R.id.action_navigation_dashboard_to_FieldInfo)
    }

    companion object {
        private const val NUMBER_OF_COLUMN = 2
    }
}
