package com.example.smarteden.generalfunctions.datemanipulation

import java.text.SimpleDateFormat
import java.util.*


fun getHarvestTime(time: Long, duration: Int): String{
    if (duration == 0)
        return "---"

    val currentDate = Calendar.getInstance() // Aktuelles Datum
    currentDate.timeInMillis = time
    currentDate.add(Calendar.MONTH, duration) // Monate hinzufügen

    val year = currentDate.get(Calendar.YEAR)
    val month = currentDate.get(Calendar.MONTH) + 1 // Monate beginnen bei 0, daher +1
    val day = currentDate.get(Calendar.DAY_OF_MONTH)

    val formattedDate = String.format(Locale.GERMAN, "%02d.%02d.%04d", day, month, year)
    //val formattedDate = format.format(Da)
    println("Neues Datum: $formattedDate")
    return formattedDate
}

fun convertLongToTime(time: Long): String {
    if(time == 0L)
        return "---"
    val date = Date(time)
    val format = SimpleDateFormat("dd.MM.yyyy")
    return format.format(date)
}

fun convertLongToFileTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd-MM-yyyy")
    return format.format(date)
}

fun oneDayBack(time: Long): Long {
    val currentDate = Calendar.getInstance() // Aktuelles Datum
    currentDate.timeInMillis = time
    currentDate.add(Calendar.DAY_OF_MONTH, -1)

    return currentDate.timeInMillis
}
