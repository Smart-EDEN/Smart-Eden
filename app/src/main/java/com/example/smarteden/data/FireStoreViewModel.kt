package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreViewModel : ViewModel() {
    lateinit var currentUserId: String

    private var _userInformation = MutableLiveData<User>()
    val userInformation: LiveData<User>
        get() = _userInformation

    private var _greenHouses = MutableLiveData<ArrayList<Greenhouse>>()
    val greenhouses: LiveData<ArrayList<Greenhouse>>
        get() = _greenHouses

    private val db = Firebase.firestore

    fun setUser(newUserId: String) {
        currentUserId = newUserId
        getUserInformation()
        getGreenhouses()
    }

    fun addUser(userId: String, email: String) {
        db.collection(USER_COLLECTION).document(userId).set(
            hashMapOf(
                "name" to "",
                "email" to email,
                "greenhouseIds" to ArrayList<String>()
            )
        )
    }

    fun getUserInformation() {
        db.collection(USER_COLLECTION).document(currentUserId).addSnapshotListener { doc, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }
            val user = User(
                doc!!.id,
                doc.data!!["name"].toString(),
                doc.data!!["email"].toString(),
                ArrayList()
            )
            _userInformation.value = user
        }
    }

    fun getGreenhouses() {
        db.collection(GREENHOUSE_COLLECTION).addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            for (doc in value!!) {
                val greenhouse = Greenhouse(doc.id)
                _greenHouses.value?.add(greenhouse)
            }
        }
    }

    companion object {
        private const val USER_COLLECTION = "User"
        private const val GREENHOUSE_COLLECTION = "SerialNumber"
        private const val TAG = "FirestoreViewModel"
    }
}
