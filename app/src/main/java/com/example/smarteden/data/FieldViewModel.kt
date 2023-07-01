package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FieldViewModel : ViewModel(){
    private val db = Firebase.firestore
    var serialNumber = "00001"
    private var _fields: MutableLiveData<ArrayList<Field>> =
        getLiveFields() as MutableLiveData<ArrayList<Field>>
    val fields: LiveData<ArrayList<Field>>
        get() = _fields
    private var _currentGreenhouse: MutableLiveData<Greenhouse> =
        getActualGreenhouse() as MutableLiveData<Greenhouse>
    val currentGreenhouse: LiveData<Greenhouse>
        get() = _currentGreenhouse

    var currentField = Field("0", 0, "Bier", 0L, 0, false, 1, 2)


    private fun getActualGreenhouse(): LiveData<Greenhouse> {
        val liveGreenhouse = MutableLiveData<Greenhouse>()
        db.collection(GREENHOUSE_COLLECTION).document(serialNumber).addSnapshotListener { docSnapshot, _ ->
            if(docSnapshot != null) {
                if (docSnapshot.exists()) {
                    val greenhouse =
                        Greenhouse(
                            docSnapshot.id,
                            docSnapshot.data!!["name"].toString(),
                            docSnapshot.data!!["Temperatur"].toString().toDouble(),
                            docSnapshot.data!!["Luftfeuchtigkeit"].toString().toInt(),
                            docSnapshot.data!!["rollo"].toString(),
                            docSnapshot.data!!["boost"].toString().toBoolean(),
                            docSnapshot.data!!["window"].toString(),
                            docSnapshot.data!!["fan"].toString(),
                            docSnapshot.data!!["manualMode"].toString().toBoolean()
                        )
                    liveGreenhouse.value = greenhouse
                    _currentGreenhouse = liveGreenhouse
                    println("Document data: $docSnapshot")
                } else {
                    println("No such document")
                }
            }
        }
        return liveGreenhouse
    }
    fun getFieldWithId(id: String) {
        //var currentField = Field("0", 0, "Bier")
        _fields.value?.forEach { field ->
            if(field.id == id)
                currentField = field
        }
    }

    fun harvestField() {
        sendNewFieldData(Field(
            currentField.id,
            humidity = 0,
            plant = "Feld leer",
            planted = 0L,
            duration = 0,
            watering = false,
            requiredHumidity = 0,
            requiredFrequencyHumidity = 0
        ))
    }


    fun setNewSerialNumber(newSerialNumber: String) {
        serialNumber = newSerialNumber
        getLiveFields()
        getActualGreenhouse()
    }

    fun plantNewPlantInField(plant: Plant, date: Long) {
        sendNewFieldData(Field(
            currentField.id,
            humidity = 0,
            plant.name,
            date,
            plant.durationMonth,
            watering = false,
            plant.requiredHumidity,
            plant.requiredFrequencyHumidity
        ))
    }

    private fun sendNewFieldData(field: Field) {
        db.collection(GREENHOUSE_COLLECTION).document(serialNumber).collection(FIELD).document(currentField.id)
            .set(field, SetOptions.merge())
    }
    private fun getLiveFields(): LiveData<ArrayList<Field>> {
        val listField = ArrayList<Field>()
        val liveField = MutableLiveData<ArrayList<Field>>()
        db.collection(GREENHOUSE_COLLECTION)
            .document(serialNumber).collection(FIELD).addSnapshotListener{ value, error ->
            listField.clear()
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            for (doc in value!!) {
                Log.d("Bier", doc.toString())
                val field = Field(
                    doc.id,
                    //doc.data["id"].toString(),
                    doc.data["humidity"].toString().toInt(),
                    doc.data["plant"].toString(),
                    doc.data["planted"].toString().toLong(),
                    doc.data["duration"] as Number,
                    doc.data["watering"] as Boolean,
                    doc.data["requiredHumidity"] as Number,
                    doc.data["requiredFrequencyHumidity"] as Number
                )
                listField.add(field)
                //_fields.value?.add(field)
            }
            liveField.value = listField
            _fields = liveField
        }
        return liveField
    }

    fun sendRolloCommand(command: String) {
        db.collection(GREENHOUSE_COLLECTION).document(serialNumber)
            .update("rollo_manuel_mode", command)
            //.collection(CONTROL).document(ROLLO).set( hashMapOf("manual" to command))
    }

    fun sendWindowCommand(command: String) {
        db.collection(GREENHOUSE_COLLECTION).document(serialNumber)
            .update("window_manuel_mode", command)
            //.collection(CONTROL).document(WINDOW).set( hashMapOf("manual" to command))
    }

    fun sendBoostCommand(command: Boolean) {
        db.collection(GREENHOUSE_COLLECTION).document(serialNumber).update("boost", command)
            //.collection(CONTROL).document(BOOST).set( hashMapOf("manual" to command))
    }

    fun sendManualMode(manualMode: Boolean) {
        db.collection(GREENHOUSE_COLLECTION).document(serialNumber)
            .set(hashMapOf("manualMode" to manualMode), SetOptions.merge() )
    }

    companion object{
        private const val TAG = "FirestoreViewModel"
        private const val GREENHOUSE_COLLECTION = "SerialNumber"
        //private const val CONTROL = "Control"
        //private const val ROLLO = "Rollo"
        //private const val WINDOW = "Window"
        //private const val BOOST = "Boost"
        private const val FIELD = "Fields"
    }
}
