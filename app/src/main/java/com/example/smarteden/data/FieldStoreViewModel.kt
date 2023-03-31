package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FieldStoreViewModel : ViewModel(){

    private var _fields = MutableLiveData<ArrayList<Field>>()
    val fields: LiveData<ArrayList<Field>>
        get() = _fields

    private val db = Firebase.firestore
    fun getFields() {
        db.collection(FIELD).addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            for (doc in value!!) {
                val field = Field(
                    doc.data!!["id"].toString(),
                    doc.data!!["humidity"].toString(),
                    doc.data!!["plant"].toString()
                )
                _fields.value?.add(field)
            }
        }
    }

    companion object{
        private const val TAG = "FirestoreViewModel"
        private const val FIELD = "Fields"
    }
}
