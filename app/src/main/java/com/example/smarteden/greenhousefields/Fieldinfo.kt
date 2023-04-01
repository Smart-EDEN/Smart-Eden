package com.example.smarteden.greenhousefields

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.smarteden.R
import com.example.smarteden.data.Field
import com.example.smarteden.data.FieldStoreViewModel
import kotlin.properties.Delegates

class Fieldinfo : Fragment() {
    var inputDataID by Delegates.notNull<Int>()
    private lateinit var viewModel: FieldStoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        inputDataID = args?.getString("FieldId").toString().toInt()
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_fieldinfo, container, false)

        val fieldArray = ArrayList<Field>()

        viewModel = ViewModelProvider(this).get(FieldStoreViewModel::class.java)
        viewModel.getFields()
        viewModel.fields.observe(viewLifecycleOwner){ fields ->
            fieldArray.addAll(fields)
            // recyclerView.adapter = RecyclerViewAdapterFieldOverview(fields,this)
        }
        //TESTARRAY:
        val testArray = ArrayList<Field>()
        testArray.add(Field("1","45%","Tomaten"))
        testArray.add(Field("2","90%","Salat"))
        testArray.add(Field("3","1%","Kaktus"))

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


        tvId.text = "Feld $inputDataID"
        tvPlant.text = "Pflanze: ${testArray[inputDataID-1].plant}"
        tvHumidity.text = "Feuchtigkeit: ${testArray[inputDataID-1].humidity}"

        return root
    }
}
