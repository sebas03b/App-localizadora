package com.example.geotracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.time.Instant
import java.util.*

data class LocEntry(val deviceId: String, val latitude: Double, val longitude: Double, val timestamp: String)

object StorageHelper {
    private const val FILE_NAME = "locations.json"

    fun saveLocation(context: Context, deviceId: String, lat: Double, lon: Double) {
        try {
            val file = File(context.filesDir, FILE_NAME)
            val gson = Gson()
            val listType = object : TypeToken<MutableList<LocEntry>>() {}.type
            val list: MutableList<LocEntry> = if (file.exists()) {
                val text = file.readText()
                if (text.isBlank()) mutableListOf() else gson.fromJson(text, listType)
            } else {
                mutableListOf()
            }

            val now = Instant.now().toString()
            list.add(LocEntry(deviceId, lat, lon, now))
            file.writeText(gson.toJson(list))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readLocations(context: Context, deviceId: String? = null): List<LocEntry> {
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return emptyList()
            val gson = Gson()
            val listType = object : TypeToken<List<LocEntry>>() {}.type
            val all = gson.fromJson<List<LocEntry>>(file.readText(), listType)
            return if (deviceId == null) all else all.filter { it.deviceId == deviceId }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}
