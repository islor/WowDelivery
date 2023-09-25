package com.ianlor.wowdelivery.feature_delivery.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class NumberFormatUtil {

    companion object {
        fun sumOfCurrency(num1: String, num2: String): String {

            val format: NumberFormat =
                NumberFormat.getCurrencyInstance(Locale.US)

            format.minimumFractionDigits = 2
            format.maximumFractionDigits = 2
            val df = DecimalFormat("0.00")
            df.roundingMode = RoundingMode.HALF_UP;

            return try {

                val fee =
                    if (num1.isEmpty()) "0" else format.parse(num1)
                val surcharge =
                    if (num2.isEmpty()) "0" else format.parse(num2)

                val sum =
                    fee.toString().toDouble() + surcharge.toString()
                        .toDouble()

                df.format(sum)?.toString() ?: "0.00"
            } catch (e: Exception) {
                println(e.message)
                "0.00"
            }
        }

        fun convertDateTime(datetime: String): String {
            var time =""
            try {

                val zonedDateTime = ZonedDateTime.parse(datetime)

                val formatter =
                    DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
                zonedDateTime.withZoneSameLocal(ZoneId.systemDefault())
                time =
                    formatter.format(zonedDateTime)
            }catch (_: DateTimeParseException){

            }
            return time
        }
    }
}