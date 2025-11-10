package com.example.geotracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geotracker.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón para enviar ubicación random
        binding.btnStartTracking.setOnClickListener { sendRandomLocation() }

        // Botón opcional para enviar ubicación simulada fija
        binding.btnSendSim.setOnClickListener { sendSimulatedLocation() }
    }

    private fun sendRandomLocation() {
        // Generar coordenadas aleatorias cerca de Bogotá
        val lat = 4.60 + Math.random() * 0.2  // de 4.60 a 4.80
        val lon = -74.20 + Math.random() * 0.2 // de -74.20 a -74.00

        // Guardar localmente
        CoroutineScope(Dispatchers.IO).launch {
            StorageHelper.saveLocation(this@MainActivity, "sim-device-1", lat, lon)
            // opcional: NetworkHelper.postLocation("sim-device-1", lat, lon)
        }

        // Actualizar UI
        binding.tvStatus.text = "Ubicación simulada enviada: $lat, $lon"
    }

    private fun sendSimulatedLocation() {
        // Coordenadas fijas: Bogotá
        val lat = 4.710989
        val lon = -74.072090

        CoroutineScope(Dispatchers.IO).launch {
            StorageHelper.saveLocation(this@MainActivity, "sim-device-1", lat, lon)
            // opcional: NetworkHelper.postLocation("sim-device-1", lat, lon)
        }

        binding.tvStatus.text = "Ubicación simulada enviada: $lat, $lon"
    }
}
