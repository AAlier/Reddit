package com.reddit.rickandmortyapp.common.network

import com.google.gson.*
import timber.log.Timber
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

private const val DATETIME_FORMAT = "dd.MM.yyyy hh:mm:sss"
private const val WEEK_DATE_DATE_TIME_FORMAT = "EEE, dd MMM yyyy hh:mm:ss 'GMT'"
private const val SHORT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
private const val SHORT_DATE_TIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val SHORT_DATE_TIME_ZONE_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DATE_FORMAT = "yyyy-MM-dd"
private const val DATE_FORMAT_DOT = "dd.MM.yyyy"

object DateTimeTypeAdapter : JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

    private val dateTimeFormat = SimpleDateFormat(SHORT_DATE_TIME_FORMAT, Locale.getDefault())
    private val items = mutableListOf(
        DATETIME_FORMAT,
        WEEK_DATE_DATE_TIME_FORMAT,
        SHORT_DATE_TIME_FORMAT,
        SHORT_DATE_TIME_ZONE_FORMAT,
        SHORT_DATE_TIME_ZONE_FULL_FORMAT,
        DATE_FORMAT,
        DATE_FORMAT_DOT
    )

    override fun serialize(
        src: Calendar?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? =
        src?.let { JsonPrimitive(dateTimeFormat.format(it.timeInMillis)) }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Calendar? {
        if (json == null) return null
        try {
            val jsonString = json.asJsonPrimitive.asString
            val calendar = Calendar.getInstance()
            items.forEach { format ->
                val dateFormat = SimpleDateFormat(format, Locale.getDefault())
                try {
                    calendar.time = dateFormat.parse(jsonString)
                    return calendar
                } catch (e: Exception) {
                    // Ignore
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error parsing date")
        }
        return null
    }
}