package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FieldViewModel : ViewModel(){
    private val db = Firebase.firestore
    var serialNumber = "00001"
    private var _fields: MutableLiveData<ArrayList<Field>> =
        getLiveFields() as MutableLiveData<ArrayList<Field>>
    val fields: LiveData<ArrayList<Field>>
        get() = _fields

    var currentField = Field("0", 0, "Bier")

    fun getFieldWithId(id: String) {
        //var currentField = Field("0", 0, "Bier")
        _fields.value?.forEach { field ->
            if(field.id == id)
                currentField = field
        }
    }


    fun setNewSerialNumber(newSerialNumber: String) {
        serialNumber = newSerialNumber
        getLiveFields()
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
                val field = Field(
                    doc.id,
                    //doc.data["id"].toString(),
                    doc.data["humidity"] as Number,
                    doc.data["plant"].toString()
                )
                listField.add(field)
                //_fields.value?.add(field)
            }
            liveField.value = listField
            _fields = liveField
        }
        return liveField
    }

    companion object{
        private const val TAG = "FirestoreViewModel"
        private const val GREENHOUSE_COLLECTION = "SerialNumber"
        private const val FIELD = "Fields"
    }
}
