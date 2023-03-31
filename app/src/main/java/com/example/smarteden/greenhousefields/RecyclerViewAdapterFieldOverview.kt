package com.example.smarteden.greenhousefields

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarteden.R

class RecyclerViewAdapterFieldOverview : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView= itemView.findViewById<TextView>(R.id.textview_title)!!
        val subtitleTextView = itemView.findViewById<TextView>(R.id.textview_subtitle)!!
        val descriptionTextView = itemView.findViewById<TextView>(R.id.textview_description)!!

    }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardviewfield, parent, false)
            val layoutParams = itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = (parent.width * multiplier).toInt()
            itemView.layoutParams = layoutParams
            return CardViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val cardViewHolder = holder as CardViewHolder
            val title = "Card ${position+1} Title"
            val subtitle = "Card ${position+1} Subtitle"
            val description = "Card ${position+1} Description"
            cardViewHolder.titleTextView.text = title
            cardViewHolder.subtitleTextView.text = subtitle
            cardViewHolder.descriptionTextView.text = description
        }

        override fun getItemCount(): Int {
            return 10
        }
    companion object{
        private const val multiplier = 0.5
    }
    }

