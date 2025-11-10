package com.example.geotracker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.geotrackerapp.utils.NetworkHelper
import com.google.android.gms.location.*

class LocationService : Service() {

    private lateinit var fusedClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    companion object {
        const val TAG = "LocationService"
        const val CHANNEL_ID = "geotracker_channel"
    }

    override fun onCreate() {
        super.onCreate()
        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10 * 60 * 1000L) // 10 min default
            .setMinUpdateIntervalMillis(60 * 1000L) // min 1 min
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (loc in result.locations) {
                    onNewLocation(loc)
                }
            }
        }

        startForegroundServiceWithNotification()
        startLocationUpdates()
    }

    private fun startForegroundServiceWithNotification() {
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(CHANNEL_ID, "GeoTracker", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(ch)
        }

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("GeoTracker activo")
            .setContentText("El servicio está obteniendo ubicación")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

// ⚠️ NO pases el entero, solo usa startForeground simple
        startForeground(1, notification)

    }

    @Suppress("MissingPermission")
    private fun startLocationUpdates() {
        fusedClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
        Log.d(TAG, "startLocationUpdates called")
    }

    private fun onNewLocation(location: Location) {
        val lat = location.latitude
        val lon = location.longitude
        Log.d(TAG, "Nueva ubicación: $lat, $lon")

        // Guardar localmente (interno)
        StorageHelper.saveLocation(this, "device-android-1", lat, lon)

        // Enviar al backend (simulado)
        Thread {
            try {
                NetworkHelper.postLocation("device-android-1", lat, lon)
            } catch (e: Exception) {
                Log.e(TAG, "Error al enviar: ${e.message}")
            }
        }.start()
    }

    override fun onDestroy() {
        fusedClient.removeLocationUpdates(locationCallback)
        stopForeground(true)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
