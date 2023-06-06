package com.example.smarteden.ui.plantnewplant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarteden.R
import com.example.smarteden.data.Plant

class PlantListAdapter(
    private val plants: ArrayList<Plant>,
    private val plantListener: PlantNewPlantFragment) : RecyclerView.Adapter<PlantListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewPlantMonths: TextView
        val textViewHarvestMonths: TextView
        val textViewGrowDuration: TextView
        val textViewGeneralInfos: TextView

        init {
            textViewName = view.findViewById(R.id.plant_name)
            textViewPlantMonths = view.findViewById(R.id.plant_plant_months)
            textViewHarvestMonths = view.findViewById(R.id.harvest_months)
            textViewGrowDuration = view.findViewById(R.id.grow_duration)
            textViewGeneralInfos = view.findViewById(R.id.general_info)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_plants, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = plants[position].name
        holder.textViewPlantMonths.text = "Pflanzmonate: " + plants[position].normalPlantMonths
        holder.textViewHarvestMonths.text = "Erntemonate: " + plants[position].normalHarvestMonths
        holder.textViewGrowDuration.text = "Monate bis Pflanze Erntereif: " + plants[position].durationMonth.toString()
        holder.textViewGeneralInfos.text = "Zusätzliche Infos: " + plants[position].additionalInfo
        holder.itemView.setOnClickListener {
            plantListener.clickPlant(plants[position])
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}
