package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class PlantViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private var _savedPlants: MutableLiveData<ArrayList<Plant>> =
        getPlants() as MutableLiveData<ArrayList<Plant>>
    val savedPlants: LiveData<ArrayList<Plant>>
        get() = _savedPlants

    private fun getPlants(): LiveData<ArrayList<Plant>> {
        val listPlants = java.util.ArrayList<Plant>()
        val listPlantsLive = MutableLiveData<java.util.ArrayList<Plant>>()

        db.collection(PLANT_COLLECTION)
            .addSnapshotListener { value, e ->
                listPlants.clear()

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (doc in value!!) {
                    try {
                        val plant = Plant(doc.id, doc.data["name"].toString(),
                            doc.data["durationMonth"].toString().toInt(),
                            doc.data["normalPlantMonths"].toString(),
                            doc.data["normalHarvestMonths"].toString(),
                            doc.data["additionalInfo"].toString())
                        listPlants.add(plant)
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "Laden fehlgeschlagen ${e.message}")
                    }
                }

                listPlantsLive.value = listPlants
                _savedPlants = listPlantsLive
            }

        return listPlantsLive
    }

    companion object{
        private const val TAG = "PlantViewModel"
        private const val PLANT_COLLECTION = "Plants"
    }
}
