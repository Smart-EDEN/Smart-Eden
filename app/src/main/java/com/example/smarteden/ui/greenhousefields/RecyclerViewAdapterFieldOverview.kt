package com.example.smarteden.ui.greenhousefields

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarteden.R
import com.example.smarteden.data.Field

class RecyclerViewAdapterFieldOverview (private val dataset: ArrayList<Field>,
                                        private val listener: FieldoverviewFragment
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(
     ) {
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvFieldId= itemView.findViewById<TextView>(R.id.textview_title)!!
        val tvHumidity= itemView.findViewById<TextView>(R.id.textview_subtitle)!!
        val tvPlant = itemView.findViewById<TextView>(R.id.textview_description)!!

    }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardviewfield, parent, false)
            val layoutParams = itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = (parent.width * multiplier).toInt()

            val height = parent.measuredHeight / 2
            itemView.minimumHeight = height
            itemView.layoutParams = layoutParams

            return CardViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val cardViewHolder = holder as CardViewHolder
            val id = "Feld ${dataset[position].id}"
            val humidity = "Feuchtigkeit: ${dataset[position].humidity}"
            val plant = "Pflanze: ${dataset[position].plant}"
            cardViewHolder.tvFieldId.text = id
            cardViewHolder.tvHumidity.text = humidity
            cardViewHolder.tvPlant.text = plant

            holder.itemView.setOnClickListener{
                listener.navigateToFragment(dataset[position].id)
            }
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    companion object{
        private const val multiplier = 0.5
    }
    }

