package com.example.smarteden.ui.greenhousefields

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smarteden.R
import com.example.smarteden.data.FieldViewModel
import java.text.SimpleDateFormat
import java.util.*

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
        val tvHumidity = root.findViewById<TextView>(R.id.humidity)
        val tvPlanted = root.findViewById<TextView>(R.id.planted_date)
        val tvHarvested = root.findViewById<TextView>(R.id.harvest_date)
        val tvWatering = root.findViewById<TextView>(R.id.watering)

        root.findViewById<Button>(R.id.btn_new_plant).setOnClickListener {
            findNavController().navigate(R.id.action_fragment_fieldinfo_to_plantNewPlantFragment)
        }

        root.findViewById<Button>(R.id.btn_harvest).setOnClickListener {
            onAlertDialog(context!!)
        }


        val currentField = fieldViewModel.currentField
        val planted = currentField.planted
        tvId.text = "Pflanze: ${currentField.plant}"
        tvHumidity.text = "Feuchtigkeit: ${currentField.humidity}%"
        tvPlanted.text = "Gepflanzt: ${convertLongToTime(planted)}"
        val harvestDate = getHarvestTime(planted, currentField.growDurationMonths.toInt())
        tvHarvested.text = "Reif: ${harvestDate}"
        tvWatering.text = if (currentField.watering) "Ein" else "Aus"



        return root
    }

    private fun onAlertDialog(context: Context) {
        //Instantiate builder variable
        val builder = AlertDialog.Builder(context)

        // set title
        builder.setTitle("Warnung")

        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //set content area
        builder.setMessage("Wollen Sie das Feld wirklich ernten und die Pflanze des Feldes zurücksetzen?")

        //set positive button
        builder.setPositiveButton(
            "Ja") { _, _ ->
            // User clicked Update Now button
        }

        //set negative button
        builder.setNegativeButton(
            "Nein") { _, _ ->
            // User cancelled the dialog
        }

        builder.show()
    }

    private fun getHarvestTime(time: Long, duration: Int): String{

        val currentDate = Calendar.getInstance() // Aktuelles Datum
        currentDate.timeInMillis = time
        currentDate.add(Calendar.MONTH, duration) // 3 Monate hinzufügen

        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH) + 1 // Monate beginnen bei 0, daher +1
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val formattedDate = String.format(Locale.GERMAN, "%02d.%02d.%04d", day, month, year)
        //val formattedDate = format.format(Da)
        println("Neues Datum: $formattedDate")
        return formattedDate
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }
/*
    private fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd")
        return df.parse(date).time
    }*/
}
