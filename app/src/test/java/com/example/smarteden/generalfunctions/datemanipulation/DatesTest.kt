package com.example.smarteden.generalfunctions.datemanipulation

import junit.framework.TestCase.assertEquals
import org.junit.Test

class DatesTest {

    @Test
    fun getHarvestTime_duration_harvestDateAsString() {
        //GIVEN - date as long. Here is the date 14.06.2023 chosen
        val dateAsLong = 1686759191402
        val expectedDateString = "14.10.2023"

        //WHEN - call the function
        val receivedDate = getHarvestTime(dateAsLong, 4)

        //THEN - check if received Date is same as expected (4 months later)
        assertEquals(expectedDateString, receivedDate)
    }

    @Test
    fun getHarvestTime_durationZero_receiveString(){
        //GIVEN - Date as long
        val dateAsLong = 1686759191402
        val expectedDateString = "---"

        //WHEN - call the function with 0 months duration
        val receivedDate = getHarvestTime(dateAsLong, 0)

        //THEN - check if received Date is same as expected (---)
        assertEquals(expectedDateString, receivedDate)
    }

    @Test
    fun convertLongToTime_longDate_actualDateAsString() {
        //GIVEN - date as long. Here is the date 14.06.2023 chosen
        val dateAsLong = 1686759191402
        val expectedDateString = "14.06.2023"

        //WHEN - call the function
        val receivedDate = convertLongToTime(dateAsLong)

        //THEN - check if received Date is same as expected
        assertEquals(expectedDateString, receivedDate)
    }

    @Test
    fun convertLongToTime_longZero_getEmptyString(){
        //GIVEN - Date as long
        val dateAsLong = 0L
        val expectedDateString = "---"

        //WHEN - call the function
        val receivedDate = convertLongToTime(dateAsLong)

        //THEN - check if received String is same as expected (---)
        assertEquals(expectedDateString, receivedDate)
    }

    @Test
    fun convertLongToFileTime_dateAsLong_DateAsFileName() {
        //GIVEN - date as long. Here is the date 14.06.2023 chosen
        val dateAsLong = 1686759191402
        val expectedDateString = "14-06-2023"

        //WHEN - call the function
        val receivedDate = convertLongToFileTime(dateAsLong)

        //THEN - check if received Date is same as expected
        assertEquals(expectedDateString, receivedDate)
    }

    @Test
    fun oneDayBack_longOfDate_longOfDateBefore() {
        //GIVEN - date as long. Here is the date 14.06.2023 chosen
        val dateAsLong = 1686759191402
        val expectedDateString = 1686672791402

        //WHEN - call the function
        val receivedDate = oneDayBack(dateAsLong)

        //THEN - check if received Date is same as expected (One day before)
        assertEquals(expectedDateString, receivedDate)
    }
}
