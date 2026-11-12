package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.MOZAMBIQUE_CITIES
import com.example.data.repository.PrayerCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Islam in Mozambique", appName)
  }

  @Test
  fun `verify qibla bearing calculation for Maputo`() {
    val maputo = MOZAMBIQUE_CITIES.first { it.name == "Maputo" }
    val bearing = PrayerCalculator.calculateQiblaBearing(maputo.latitude, maputo.longitude)
    // From Maputo (-25.9692, 32.5732), Qibla bearing is 9.11° North-Northeast
    assertTrue(bearing in 8.0f..11.0f)
  }

  @Test
  fun `verify prayer times calculated for Maputo`() {
    val maputo = MOZAMBIQUE_CITIES.first { it.name == "Maputo" }
    val cal = Calendar.getInstance()
    val prayers = PrayerCalculator.calculatePrayerTimes(maputo, cal)
    assertEquals(6, prayers.size) // Fajr, Shuruq, Dhuhr, Asr, Maghrib, Isha
  }
}
