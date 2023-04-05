package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    lateinit var auth: FirebaseAuth
    lateinit var currentUserId: String

    private var _userInformation = MutableLiveData<User>()
    val userInformation: LiveData<User>
        get() = _userInformation

    private var _greenhouses = MutableLiveData<ArrayList<Greenhouse>>()
    val greenhouses: LiveData<ArrayList<Greenhouse>>
        get() = _greenhouses

    fun setUser(newUserId: String, auth: FirebaseAuth) {
        currentUserId = newUserId
        this.auth = auth
        getUserInformation()
    }

    fun addUser(userId: String, email: String, auth: FirebaseAuth) {
        currentUserId = userId
        this.auth = auth
        db.collection(USER_COLLECTION).document(userId).set(
            hashMapOf(
                "name" to "",
                "email" to email,
                "greenhouseIds" to ArrayList<String>()
            )
        )
    }

    fun connectGreenhouseWithUser(serialNumber: String, password: String): Boolean {
        if (isValid(serialNumber, password)) {
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
            return true
        }
        return false
    }

    private fun isValid(serialNumber: String, password: String): Boolean {
        if(serialNumber.isNotEmpty() && password.isNotEmpty())
            return true
        return false
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

    private fun getUserInformation() {
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
            greenhouseIds.forEach { id ->
                getLiveGreenhouses(id)
            }
            _userInformation.value = user
        }
    }


    private fun getLiveGreenhouses(serialNumber: String) {
        val docRef = db.collection(GREENHOUSE_COLLECTION).document(serialNumber)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    val greenhouse = Greenhouse(document.id, document.data!!["name"].toString())
                    if(_greenhouses.value != null) {
                        var idExists = false
                        greenhouses.value?.forEach { greenhouseId ->
                            if(greenhouseId.id == serialNumber)
                                idExists = true
                        }
                        if(!idExists)
                            _greenhouses.value?.add(greenhouse)
                    } else
                        _greenhouses.value = arrayListOf(greenhouse)

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun logUserOut() {
        auth.signOut()
    }

    companion object {
        private const val USER_COLLECTION = "User"
        private const val GREENHOUSE_COLLECTION = "SerialNumber"
        private const val TAG = "FirestoreViewModel"
    }
}
