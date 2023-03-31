package com.example.smarteden.greenhousefields

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarteden.R
import com.example.smarteden.data.Field

class FieldoverviewFragment : Fragment(
    ) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fieldoverview, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_fields)
        recyclerView.layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMN)//LinearLayoutManager(requireContext())

        //später über viewmodel:
        val test = ArrayList<Field>()
        test.add(Field("1","wett as hell","WEED"))
        //
        recyclerView.adapter = RecyclerViewAdapterFieldOverview(test)
        return view
    }
    companion object {
        private const val NUMBER_OF_COLUMN = 2
    }
}
