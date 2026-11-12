package com.example.data.model

import java.time.LocalDate

data class PrayerTime(
    val name: String,
    val arabicName: String,
    val timeFormatted: String,
    val isNext: Boolean = false,
    val isPassed: Boolean = false
)

data class CityLocation(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val timezoneHours: Double
)

enum class CalculationMethod(val displayName: String, val fajrAngle: Double, val ishaAngle: Double) {
    MUSLIM_WORLD_LEAGUE("Liga Islâmica Mundial (MWL)", 18.0, 17.0),
    EGYPTIAN("Autoridade Geral Egípcia", 19.5, 17.5),
    UMM_AL_QURA("Umm Al-Qura (Makkah)", 18.5, 19.0),
    ISNA("Sociedade Islâmica da América do Norte (ISNA)", 15.0, 15.0),
    MOZAMBIQUE_STANDARD("Padrão Comunidade Moçambique", 18.0, 18.0)
}

val MOZAMBIQUE_CITIES = listOf(
    CityLocation("Maputo", "Moçambique", -25.9692, 32.5732, 2.0),
    CityLocation("Matola", "Moçambique", -25.9622, 32.4589, 2.0),
    CityLocation("Beira", "Moçambique", -19.8436, 34.8389, 2.0),
    CityLocation("Nampula", "Moçambique", -15.1165, 39.2666, 2.0),
    CityLocation("Quelimane", "Moçambique", -17.8786, 36.8883, 2.0),
    CityLocation("Tete", "Moçambique", -16.1564, 33.5864, 2.0),
    CityLocation("Chimoio", "Moçambique", -19.1164, 33.4833, 2.0),
    CityLocation("Pemba", "Moçambique", -12.9732, 40.5178, 2.0),
    CityLocation("Inhambane", "Moçambique", -23.8650, 35.3833, 2.0),
    CityLocation("Lichinga", "Moçambique", -13.3128, 35.2406, 2.0),
    CityLocation("Nacala", "Moçambique", -14.5428, 40.6728, 2.0),
    CityLocation("Xai-Xai", "Moçambique", -25.0519, 33.6442, 2.0),
    CityLocation("Lisboa", "Portugal", 38.7223, -9.1393, 0.0),
    CityLocation("Luanda", "Angola", -8.8390, 13.2894, 1.0),
    CityLocation("Makkah", "Arábia Saudita", 21.4225, 39.8262, 3.0),
    CityLocation("Madinah", "Arábia Saudita", 24.5247, 39.5692, 3.0)
)
