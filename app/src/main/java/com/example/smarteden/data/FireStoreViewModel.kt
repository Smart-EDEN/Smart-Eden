package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreViewModel : ViewModel() {
    lateinit var currentUserId: String

    private var _userInformation = MutableLiveData<User>()
    val userInformation: LiveData<User>
        get() = _userInformation

    private var _greenhouses = MutableLiveData<ArrayList<Greenhouse>>()
        //getLiveGreenhouses() as MutableLiveData<ArrayList<Greenhouse>>
    val greenhouses: LiveData<ArrayList<Greenhouse>>
        get() = _greenhouses

    private val db = Firebase.firestore

    fun setUser(newUserId: String) {
        currentUserId = newUserId
        getUserInformation()
        getLiveGreenhouses()
        Log.d("BeneView", "Greenhouse war called")
    }

    fun addUser(userId: String, email: String) {
        currentUserId = userId
        db.collection(USER_COLLECTION).document(userId).set(
            hashMapOf(
                "name" to "",
                "email" to email,
                "greenhouseIds" to ArrayList<String>()
            )
        )
    }

    fun connectGreenhouseWithUser(serialNumber: String, password: String) {
        db.collection(GREENHOUSE_COLLECTION).document(serialNumber).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (document["password"].toString() == password) {
                        //Password and serial number correct
                        checkSerialNumbersOfUser(serialNumber)
                    } else {
                        //Password false
                    }
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    private fun checkSerialNumbersOfUser(serialNumber: String) {
        val user = userInformation.value!!
        var serialNumberAlreadyExists = false
        user.greenhouseIds.forEach { id ->
            if (id == serialNumber)
                serialNumberAlreadyExists = true
        }
        Log.d("CheckSerialNumber", serialNumberAlreadyExists.toString())
        if (!serialNumberAlreadyExists)
            addSerialNumber(serialNumber)

        /*db.collection(USER_COLLECTION).document(currentUserId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var idAlreadyExists = false
                    val serialNumbersList = document["greenhouseIds"] as ArrayList<String>
                    serialNumbersList.forEach { id ->
                        if(id == serialNumber)
                            idAlreadyExists = true
                    }
                    if(!idAlreadyExists) {
                        serialNumbersList.add(serialNumber)
                        addSerialNumber(serialNumber, serialNumbersList)
                    }
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }*/
    }

    private fun addSerialNumber(serialNumber: String) {
        Log.d("Bene", "addSerialNumber")
        val updates = hashMapOf<String, Any>(
            "greenhouseIds" to FieldValue.arrayUnion(serialNumber)
        )
        db.collection(USER_COLLECTION).document(currentUserId).update(
            updates

        )
    }

    fun getUserInformation() {
        db.collection(USER_COLLECTION).document(currentUserId).addSnapshotListener { doc, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }
            val greenhouseIds = doc!!.data?.get("greenhouseIds") as ArrayList<*>
            val user = User(
                doc.id,
                doc.data!!["name"].toString(),
                doc.data!!["email"].toString(),
                greenhouseIds as ArrayList<String>
            )
            _userInformation.value = user
        }
    }


    fun getLiveGreenhouses(): LiveData<ArrayList<Greenhouse>> {
        val liveGreenhouses = MutableLiveData<ArrayList<Greenhouse>>()
        val listGreenhouses = ArrayList<Greenhouse>()
        db.collection(GREENHOUSE_COLLECTION).addSnapshotListener { value, error ->
            listGreenhouses.clear()
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            for (doc in value!!) {
                val greenhouse = Greenhouse(doc.id, doc.data["name"].toString())
                userInformation.value?.greenhouseIds?.forEach { greenhouseId ->
                    if(greenhouse.id == greenhouseId)
                        listGreenhouses.add(greenhouse)
                }
            }
            liveGreenhouses.value = listGreenhouses
            _greenhouses = liveGreenhouses
        }
        return liveGreenhouses
    }

    companion object {
        private const val USER_COLLECTION = "User"
        private const val GREENHOUSE_COLLECTION = "SerialNumber"
        private const val TAG = "FirestoreViewModel"
    }
}
