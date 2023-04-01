package com.example.smarteden.greenhousefields

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.smarteden.R

class Fieldinfo : Fragment() {
    lateinit var inputData : String

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        inputData = args?.getString("FieldId").toString()
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_fieldinfo, container, false)

        val tvId = root.findViewById<TextView>(R.id.tvId)

        tvId.text = inputData

        return root
    }
}
