package com.example.smarteden.ui.greenhousefields

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.smarteden.R
import com.example.smarteden.data.FieldViewModel

class Fieldinfo : Fragment() {

    private val fieldViewModel: FieldViewModel by activityViewModels()
    //var inputDataID by Delegates.notNull<Int>()
    //private lateinit var viewModel: FieldViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        //val args = arguments

        //inputDataID = args?.getString("FieldId").toString().toInt()
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_fieldinfo, container, false)

        val tvId = root.findViewById<TextView>(R.id.field_name)
        val tvPlant = root.findViewById<TextView>(R.id.plant_name)
        val tvHumidity = root.findViewById<TextView>(R.id.humidity)
        val slider = root.findViewById<SeekBar>(R.id.moisture_seek_bar)
        val sliderValueTextView = root.findViewById<TextView>(R.id.sliderValueTextView)

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Den aktuellen Wert des Sliders abrufen und im TextView anzeigen
                sliderValueTextView.text = "$progress %"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //wird benötigt auch wenn detekt nicht will
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //wird benötigt auch wenn detekt nicht will
            }
        })


        val currentField = fieldViewModel.currentField
        tvId.text = "Feld ${currentField.id}"
        tvPlant.text = "Pflanze: ${currentField.plant}"
        tvHumidity.text = "Feuchtigkeit: ${currentField.humidity}%"

        return root
    }
}
