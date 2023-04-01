package com.example.smarteden.greenhousefields

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        val testArray = ArrayList<Field>()
        testArray.add(Field("1","45%","Tomaten"))
        testArray.add(Field("2","90%","Salat"))
        testArray.add(Field("3","1%","Kaktus"))
        //with viewModel:
        viewModel.getFields()


        viewModel.fields.observe(viewLifecycleOwner){ fields ->
           // recyclerView.adapter = RecyclerViewAdapterFieldOverview(fields,this)
        }
        //nothing in here ?
        Log.d("Bier", viewModel.fields.value.toString())
        recyclerView.adapter = RecyclerViewAdapterFieldOverview(testArray,this)
        return view
    }

    fun navigateToFragment(id: String) {
        val bundle = Bundle()
        bundle.putString("FieldId",id)
        val fragment = Fieldinfo()
        fragment.arguments = bundle
        findNavController().navigate(R.id.action_navigation_dashboard_to_FieldInfo, bundle)
        /*parentFragmentManager.beginTransaction()
            .replace(this.id, )
            .commit()*/
    }

    companion object {
        private const val NUMBER_OF_COLUMN = 2
    }
}
