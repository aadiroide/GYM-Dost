package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.GymDostRepository
import com.example.model.ActivityLevel
import com.example.model.FitnessGoal
import com.example.model.IntakeItem
import com.example.model.TargetTimeline
import com.example.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `verify app name resource`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("GYM Dost", appName)
  }

  @Test
  fun `verify user profile calculations for bulk goal`() {
    val profile = UserProfile(
      name = "Rahul Sharma",
      gender = "male",
      age = 24,
      weightKg = 70f,
      heightCm = 175f,
      goal = FitnessGoal.BULK,
      timeline = TargetTimeline.SIX_MONTHS,
      activityLevel = ActivityLevel.MODERATE
    )

    // BMR = 10*70 + 6.25*175 - 5*24 + 5 = 700 + 1093.75 - 120 + 5 = 1678.75
    // TDEE = 1678.75 * 1.55 = 2602
    // Bulk surplus = +350 => ~2952 kcal
    assertTrue(profile.calculatedCalories in 2500..3200)

    // Protein: 70 * 2.1 = 147g
    assertEquals(147, profile.calculatedProtein)

    // Water: 70 * 0.045 = 3.15L
    assertEquals(3.15f, profile.calculatedWaterLiters, 0.1f)

    // BMI: 70 / (1.75^2) = 22.85
    assertEquals(22.85f, profile.bmi, 0.2f)
    assertEquals("Healthy Weight", profile.bmiCategory)
  }

  @Test
  fun `verify repository intake logging and reset`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = GymDostRepository(context)

    repo.resetToday()
    assertEquals(0, repo.intakeLogs.value.size)
    assertEquals(0, repo.waterGlasses.value)

    // Add 100g raw peanuts (567 kcal, 26g protein)
    val peanuts = IntakeItem(
      id = "test_1",
      name = "100g Raw Peanuts",
      calories = 567,
      protein = 26,
      mealTiming = "Snack"
    )
    repo.addIntakeItem(peanuts)
    assertEquals(1, repo.intakeLogs.value.size)
    assertEquals(567, repo.intakeLogs.value[0].calories)
    assertEquals(26, repo.intakeLogs.value[0].protein)

    // Add water
    repo.addWaterGlass()
    assertEquals(1, repo.waterGlasses.value)

    // Reset today
    repo.resetToday()
    assertEquals(0, repo.intakeLogs.value.size)
    assertEquals(0, repo.waterGlasses.value)
  }

  @Test
  fun `verify exercise toggle and custom exercise addition`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = GymDostRepository(context)

    val monList = repo.routines.value["mon"] ?: emptyList()
    assertTrue(monList.isNotEmpty())
    val firstExId = monList[0].id
    val initialCompleted = monList[0].isCompleted

    repo.toggleExercise("mon", firstExId)
    val updatedMon = repo.routines.value["mon"] ?: emptyList()
    assertEquals(!initialCompleted, updatedMon[0].isCompleted)

    // Add custom exercise
    repo.addCustomExercise("mon", "Incline Cable Flyes", "Upper Chest", "3 Sets × 12 Reps")
    val withCustom = repo.routines.value["mon"] ?: emptyList()
    val added = withCustom.firstOrNull { it.name == "Incline Cable Flyes" }
    assertTrue(added != null)
    assertEquals("Upper Chest", added?.targetMuscle)
  }
}
