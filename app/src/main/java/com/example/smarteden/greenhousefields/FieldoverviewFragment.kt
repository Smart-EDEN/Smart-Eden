package com.example.smarteden.greenhousefields

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarteden.R
import com.example.smarteden.data.Field
import com.example.smarteden.data.FieldStoreViewModel

class FieldoverviewFragment : Fragment(
    ) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: FieldStoreViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fieldoverview, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_fields)
        recyclerView.layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMN)//LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(FieldStoreViewModel::class.java)

        viewModel.getFields()

        var fieldArray = ArrayList<Field>()
        viewModel.fields.observe(this, Observer {
            fieldArray = it
        })

        recyclerView.adapter = RecyclerViewAdapterFieldOverview(fieldArray)
        return view
    }
    companion object {
        private const val NUMBER_OF_COLUMN = 2
    }
}
