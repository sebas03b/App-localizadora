package com.example.geotrackerapp.utils

import android.util.Log

object NetworkHelper {
    fun sendLocation(latitude: Double, longitude: Double) {
        // 🔧 Simulación de envío de datos (solo muestra en consola)
        Log.d("NetworkHelper", "Ubicación enviada: Lat=$latitude, Lon=$longitude")
    }
    // 🔧 Simulación del envío al backend
    fun postLocation(deviceId: String, latitude: Double, longitude: Double) {
        // En una versión real usarías OkHttp o Retrofit para mandar la data a tu servidor.
        Log.d("NetworkHelper", "📡 Enviando ubicación => $deviceId: ($latitude, $longitude)")
    }
}
