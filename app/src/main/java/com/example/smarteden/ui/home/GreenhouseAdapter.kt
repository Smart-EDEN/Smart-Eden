package com.example.smarteden.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarteden.R
import com.example.smarteden.data.Greenhouse

class GreenhouseAdapter (
    private val dataSet: ArrayList<Greenhouse>,
    private val homeFragment: HomeFragment
) : RecyclerView.Adapter<GreenhouseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val greenhouseName: TextView
        val serialNumber: TextView

        init {
            greenhouseName = view.findViewById(R.id.greenhouse_name)
            serialNumber = view.findViewById(R.id.greenhouse_serial_number)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_greenhouse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.serialNumber.text = dataSet[position].id
        holder.greenhouseName.text = dataSet[position].name

        holder.itemView.setOnClickListener {
            homeFragment.navigateToFieldOverview()
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
