package com.example.data.repository

import com.example.data.model.CalculationMethod
import com.example.data.model.CityLocation
import com.example.data.model.PrayerTime
import java.util.Calendar
import java.util.Locale
import kotlin.math.*

object PrayerCalculator {

    private const val KAABA_LAT = 21.4225
    private const val KAABA_LNG = 39.8262

    /**
     * Calculates the Qibla azimuth from a given coordinate in degrees (0-360, where 0 is True North).
     */
    fun calculateQiblaBearing(lat: Double, lng: Double): Float {
        val latRad = Math.toRadians(lat)
        val lngRad = Math.toRadians(lng)
        val kaabaLatRad = Math.toRadians(KAABA_LAT)
        val kaabaLngRad = Math.toRadians(KAABA_LNG)

        val deltaLng = kaabaLngRad - lngRad
        val y = sin(deltaLng) * cos(kaabaLatRad)
        val x = cos(latRad) * sin(kaabaLatRad) - sin(latRad) * cos(kaabaLatRad) * cos(deltaLng)

        var bearing = Math.toDegrees(atan2(y, x))
        bearing = (bearing + 360.0) % 360.0
        return bearing.toFloat()
    }

    /**
     * Calculates prayer times for a city and date.
     */
    fun calculatePrayerTimes(
        city: CityLocation,
        calendar: Calendar = Calendar.getInstance(),
        method: CalculationMethod = CalculationMethod.MOZAMBIQUE_STANDARD
    ): List<PrayerTime> {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentTotalMinutes = currentHour * 60 + currentMinute

        // Julian Date
        val jd = toJulianDate(year, month, day)
        val d = jd - 2451545.0 // days since J2000.0

        // Sun's mean anomaly & longitude
        val g = fixAngle(357.529 + 0.98560028 * d)
        val q = fixAngle(280.459 + 0.98564736 * d)
        val l = fixAngle(q + 1.915 * sin(Math.toRadians(g)) + 0.020 * sin(Math.toRadians(2 * g)))

        // Earth's obliquity of ecliptic
        val e = 23.439 - 0.00000036 * d
        val declination = Math.toDegrees(asin(sin(Math.toRadians(e)) * sin(Math.toRadians(l))))

        // Right ascension & Equation of time
        var ra = Math.toDegrees(atan2(cos(Math.toRadians(e)) * sin(Math.toRadians(l)), cos(Math.toRadians(l)))) / 15.0
        ra = fixHour(ra)
        val eqT = (q / 15.0) - ra

        // Solar noon (Dhuhr)
        val noon = fixHour(12.0 + city.timezoneHours - (city.longitude / 15.0) - eqT)

        // Hour angle helper
        fun hourAngle(angle: Double): Double {
            val num = -sin(Math.toRadians(angle)) - sin(Math.toRadians(city.latitude)) * sin(Math.toRadians(declination))
            val denom = cos(Math.toRadians(city.latitude)) * cos(Math.toRadians(declination))
            val ratio = (num / denom).coerceIn(-1.0, 1.0)
            return Math.toDegrees(acos(ratio)) / 15.0
        }

        // Asr hour angle (Shafi'i shadow = 1)
        val asrAngle = -Math.toDegrees(atan(1.0 + tan(Math.toRadians(abs(city.latitude - declination)))))
        val asrHa = hourAngle(-asrAngle)

        val fajrHa = hourAngle(method.fajrAngle)
        val sunriseHa = hourAngle(0.833) // solar depression for refraction
        val sunsetHa = hourAngle(0.833)
        val ishaHa = hourAngle(method.ishaAngle)

        val fajrTime = fixHour(noon - fajrHa)
        val sunriseTime = fixHour(noon - sunriseHa)
        val dhuhrTime = fixHour(noon)
        val asrTime = fixHour(noon + asrHa)
        val maghribTime = fixHour(noon + sunsetHa)
        val ishaTime = fixHour(noon + ishaHa)

        val prayerHours = listOf(
            Triple("Fajr", "الفجر", fajrTime),
            Triple("Shuruq", "الشروق", sunriseTime),
            Triple("Dhuhr", "الظهر", dhuhrTime),
            Triple("Asr", "العصر", asrTime),
            Triple("Maghrib", "المغرب", maghribTime),
            Triple("Isha", "العشاء", ishaTime)
        )

        val prayerMinutes = prayerHours.map { (name, arabic, hours) ->
            val totalMins = (hours * 60).roundToInt()
            Triple(name, arabic, totalMins)
        }

        // Determine which prayer is next
        var nextFound = false
        val list = prayerMinutes.mapIndexed { index, (name, arabic, totalMins) ->
            val h = totalMins / 60
            val m = totalMins % 60
            val formatted = String.format(Locale.getDefault(), "%02d:%02d", h, m)
            val isPassed = totalMins < currentTotalMinutes
            var isNext = false

            if (!isPassed && !nextFound && name != "Shuruq") {
                isNext = true
                nextFound = true
            }

            PrayerTime(
                name = name,
                arabicName = arabic,
                timeFormatted = formatted,
                isNext = isNext,
                isPassed = isPassed
            )
        }

        // If all prayers passed today, tomorrow's Fajr is next
        if (!nextFound) {
            return list.mapIndexed { i, pt ->
                if (i == 0) pt.copy(isNext = true) else pt
            }
        }

        return list
    }

    /**
     * Accurate Islamic (Hijri) date calculation using official Java/Android Hijrah calendar when available.
     */
    fun getHijriDate(calendar: Calendar = Calendar.getInstance()): String {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val y = calendar.get(Calendar.YEAR)
                val m = calendar.get(Calendar.MONTH) + 1
                val d = calendar.get(Calendar.DAY_OF_MONTH)
                val localDate = java.time.LocalDate.of(y, m, d)
                val hijrah = java.time.chrono.HijrahDate.from(localDate)
                val day = hijrah.get(java.time.temporal.ChronoField.DAY_OF_MONTH)
                val month = hijrah.get(java.time.temporal.ChronoField.MONTH_OF_YEAR)
                val year = hijrah.get(java.time.temporal.ChronoField.YEAR_OF_ERA)

                val hijriMonths = listOf(
                    "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
                    "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban",
                    "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
                )
                val monthName = hijriMonths.getOrElse(month - 1) { "Jumada al-Thani" }
                return "$day de $monthName, $year AH"
            }
        } catch (_: Exception) {}

        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH) + 1
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        var jd = toJulianDate(y, m, d)
        val l = jd - 1948440 + 10632
        val n = ((l - 1) / 10631).toInt()
        val l2 = l - 10631 * n + 354
        val j = (((10985 - l2) / 5316).toInt()) * ((50 * l2) / 17719).toInt() +
                ((l2 / 5670).toInt()) * ((43 * l2) / 15238).toInt()
        val l3 = l2 - (((30 - j) / 15).toInt()) * ((17719 * j) / 50).toInt() -
                ((j / 16).toInt()) * ((15238 * j) / 43).toInt() + 29
        val month = ((24 * l3) / 709).toInt().coerceIn(1, 12)
        var day = (l3 - ((709 * month) / 24).toInt()).toInt()
        if (day <= 0) day = 2
        val year = (30 * n + j - 30).toInt()

        val hijriMonths = listOf(
            "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
            "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban",
            "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
        )
        return "$day de ${hijriMonths[month - 1]}, $year AH"
    }

    fun getRealGregorianDateFormatted(calendar: Calendar = Calendar.getInstance()): String {
        val daysOfWeek = listOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado")
        val months = listOf(
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        )
        val dayOfWeek = daysOfWeek.getOrElse(calendar.get(Calendar.DAY_OF_WEEK) - 1) { "Quinta-feira" }
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = months.getOrElse(calendar.get(Calendar.MONTH)) { "Novembro" }
        val year = calendar.get(Calendar.YEAR)
        return "$dayOfWeek, $day de $month de $year"
    }

    private fun toJulianDate(year: Int, month: Int, day: Int): Double {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5
    }

    private fun fixAngle(angle: Double): Double {
        var a = angle - 360.0 * floor(angle / 360.0)
        a = if (a < 0) a + 360.0 else a
        return a
    }

    private fun fixHour(hour: Double): Double {
        var h = hour - 24.0 * floor(hour / 24.0)
        h = if (h < 0) h + 24.0 else h
        return h
    }
}
