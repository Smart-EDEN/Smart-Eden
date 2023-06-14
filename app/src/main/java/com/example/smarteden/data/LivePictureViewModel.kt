package com.example.smarteden.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarteden.generalfunctions.datemanipulation.convertLongToFileTime
import com.example.smarteden.generalfunctions.datemanipulation.oneDayBack
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class LivePictureViewModel : ViewModel() {
    private val storage = Firebase.storage("gs://smarteden-oth.appspot.com")
    //private val storageRef = storage.reference

    private var serialNumber = ""
    private var _imageUrls = MutableLiveData<ArrayList<String>>()
    val imageUrls: LiveData<ArrayList<String>>
        get() = _imageUrls

    private var _today = MutableLiveData<String>()
    val today: LiveData<String>
        get() = _today

    private val _allPicturesLoaded = MutableLiveData(false)
    val allPicturesLoaded: LiveData<Boolean>
        get() = _allPicturesLoaded

    fun changeSerialNumber(newSerialNumber: String) {
        serialNumber = newSerialNumber
    }

    fun getToday() {
        //val arrayOfImages = ArrayList<String>()
        //val liveImages = MutableLiveData<ArrayList<String>>()
        viewModelScope.launch {
            val currentDate = Calendar.getInstance() // Aktuelles Datum

            val date = convertLongToFileTime(currentDate.timeInMillis)
            Log.d("Bier", date)
            var newValue = getStorage("$date.jpg")
            if(newValue == "") {
                currentDate.timeInMillis = oneDayBack(currentDate.timeInMillis)
                val date1 = convertLongToFileTime(currentDate.timeInMillis)
                Log.d("Bier", date1)
                newValue = getStorage("$date1.jpg")
            }

            _today.value = newValue
        }
    }

    fun getLastImages() /*: LiveData<ArrayList<String>>*/ {
        val arrayOfImages = ArrayList<String>()
        val liveImages = MutableLiveData<ArrayList<String>>()
        val currentDate = Calendar.getInstance()

        viewModelScope.launch {
            repeat(NUMBER_OF_PICTURES_FOR_VIDEO) {
                val date = convertLongToFileTime(currentDate.timeInMillis)
                val newValue = getStorage("$date.jpg")
                Log.d("Bier", newValue)
                if (newValue != "")
                    arrayOfImages.add(newValue)

                currentDate.timeInMillis = oneDayBack(currentDate.timeInMillis)
            }
            arrayOfImages.reverse()
            liveImages.value = arrayOfImages
            _imageUrls.value = liveImages.value
            _allPicturesLoaded.value = true
        }

    }

    private suspend fun getStorage(filename: String): String {
        return withContext(Dispatchers.IO) {
            //val storage = Firebase.storage
            val imagePath = "$serialNumber/$filename"

            return@withContext try {
                storage.reference.child(imagePath).downloadUrl.await().toString()

            } catch (e: StorageException) {
                e.printStackTrace()
                ""
            } catch (e: CancellationException) {
                e.printStackTrace()
                ""
            }
        }
    }

    companion object{
        private const val NUMBER_OF_PICTURES_FOR_VIDEO = 30
    }
}
