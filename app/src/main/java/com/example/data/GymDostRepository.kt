package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.ActivityLevel
import com.example.model.ExerciseItem
import com.example.model.FitnessGoal
import com.example.model.IntakeItem
import com.example.model.TargetTimeline
import com.example.model.UserProfile
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class GymDostRepository(context: Context) {

  private val prefs: SharedPreferences = context.getSharedPreferences("gym_dost_prefs", Context.MODE_PRIVATE)
  private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

  private val intakeListType = Types.newParameterizedType(List::class.java, IntakeItem::class.java)
  private val intakeAdapter = moshi.adapter<List<IntakeItem>>(intakeListType)

  private val exerciseListType = Types.newParameterizedType(List::class.java, ExerciseItem::class.java)
  private val exerciseAdapter = moshi.adapter<List<ExerciseItem>>(exerciseListType)

  private val _userProfile = MutableStateFlow(loadProfile())
  val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

  private val _intakeLogs = MutableStateFlow(loadIntakeLogs())
  val intakeLogs: StateFlow<List<IntakeItem>> = _intakeLogs.asStateFlow()

  private val _routines = MutableStateFlow(loadRoutines())
  val routines: StateFlow<Map<String, List<ExerciseItem>>> = _routines.asStateFlow()

  private val _waterGlasses = MutableStateFlow(prefs.getInt("water_glasses", 0))
  val waterGlasses: StateFlow<Int> = _waterGlasses.asStateFlow()

  // Profile Operations
  fun saveProfile(profile: UserProfile) {
    _userProfile.value = profile
    prefs.edit()
      .putString("user_name", profile.name)
      .putString("user_gender", profile.gender)
      .putInt("user_age", profile.age)
      .putFloat("user_weight", profile.weightKg)
      .putFloat("user_height", profile.heightCm)
      .putString("user_goal", profile.goal.name)
      .putString("user_timeline", profile.timeline.name)
      .putString("user_activity", profile.activityLevel.name)
      .putBoolean("user_configured", true)
      .apply()
  }

  private fun loadProfile(): UserProfile {
    val configured = prefs.getBoolean("user_configured", false)
    val name = prefs.getString("user_name", "Rahul Sharma") ?: "Rahul Sharma"
    val gender = prefs.getString("user_gender", "male") ?: "male"
    val age = prefs.getInt("user_age", 24)
    val weight = prefs.getFloat("user_weight", 70f)
    val height = prefs.getFloat("user_height", 175f)
    val goalStr = prefs.getString("user_goal", FitnessGoal.BULK.name)
    val timelineStr = prefs.getString("user_timeline", TargetTimeline.SIX_MONTHS.name)
    val activityStr = prefs.getString("user_activity", ActivityLevel.MODERATE.name)

    val goal = runCatching { FitnessGoal.valueOf(goalStr ?: "") }.getOrDefault(FitnessGoal.BULK)
    val timeline = runCatching { TargetTimeline.valueOf(timelineStr ?: "") }.getOrDefault(TargetTimeline.SIX_MONTHS)
    val activity = runCatching { ActivityLevel.valueOf(activityStr ?: "") }.getOrDefault(ActivityLevel.MODERATE)

    return UserProfile(
      name = name,
      gender = gender,
      age = age,
      weightKg = weight,
      heightCm = height,
      goal = goal,
      timeline = timeline,
      activityLevel = activity,
      isConfigured = configured
    )
  }

  // Intake Log Operations
  fun addIntakeItem(item: IntakeItem) {
    val current = _intakeLogs.value.toMutableList()
    current.add(0, item)
    _intakeLogs.value = current
    saveIntakeLogs(current)
  }

  fun removeIntakeItem(id: String) {
    val current = _intakeLogs.value.filter { it.id != id }
    _intakeLogs.value = current
    saveIntakeLogs(current)
  }

  private fun saveIntakeLogs(items: List<IntakeItem>) {
    val json = intakeAdapter.toJson(items)
    prefs.edit().putString("intake_logs_json", json).apply()
  }

  private fun loadIntakeLogs(): List<IntakeItem> {
    val json = prefs.getString("intake_logs_json", null) ?: return emptyList()
    return runCatching { intakeAdapter.fromJson(json) }.getOrNull() ?: emptyList()
  }

  // Routine & Exercise Operations
  fun toggleExercise(dayKey: String, exerciseId: String) {
    val currentMap = _routines.value.toMutableMap()
    val dayList = currentMap[dayKey]?.toMutableList() ?: return
    val idx = dayList.indexOfFirst { it.id == exerciseId }
    if (idx != -1) {
      val ex = dayList[idx]
      dayList[idx] = ex.copy(isCompleted = !ex.isCompleted)
      currentMap[dayKey] = dayList
      _routines.value = currentMap
      saveRoutines(currentMap)
    }
  }

  fun addCustomExercise(dayKey: String, name: String, targetMuscle: String, setsAndReps: String) {
    val currentMap = _routines.value.toMutableMap()
    val dayList = (currentMap[dayKey] ?: emptyList()).toMutableList()
    val newEx = ExerciseItem(
      id = "custom_" + System.currentTimeMillis(),
      name = name,
      targetMuscle = targetMuscle,
      setsAndReps = setsAndReps,
      dayKey = dayKey,
      isCompleted = false,
      isCustom = true
    )
    dayList.add(newEx)
    currentMap[dayKey] = dayList
    _routines.value = currentMap
    saveRoutines(currentMap)
  }

  fun removeExercise(dayKey: String, exerciseId: String) {
    val currentMap = _routines.value.toMutableMap()
    val dayList = currentMap[dayKey]?.filter { it.id != exerciseId } ?: return
    currentMap[dayKey] = dayList
    _routines.value = currentMap
    saveRoutines(currentMap)
  }

  private fun saveRoutines(routines: Map<String, List<ExerciseItem>>) {
    val editor = prefs.edit()
    routines.forEach { (day, list) ->
      val json = exerciseAdapter.toJson(list)
      editor.putString("routine_$day", json)
    }
    editor.apply()
  }

  private fun loadRoutines(): Map<String, List<ExerciseItem>> {
    val result = mutableMapOf<String, List<ExerciseItem>>()
    val days = listOf("mon", "tue", "wed", "thu", "fri", "sat", "sun")
    days.forEach { day ->
      val json = prefs.getString("routine_$day", null)
      val list = if (json != null) {
        runCatching { exerciseAdapter.fromJson(json) }.getOrNull()
      } else null
      result[day] = list ?: GymDostData.INITIAL_ROUTINES[day] ?: emptyList()
    }
    return result
  }

  // Water Tracker
  fun addWaterGlass() {
    val newCount = _waterGlasses.value + 1
    _waterGlasses.value = newCount
    prefs.edit().putInt("water_glasses", newCount).apply()
  }

  // Reset Today
  fun resetToday() {
    _intakeLogs.value = emptyList()
    saveIntakeLogs(emptyList())

    _waterGlasses.value = 0
    prefs.edit().putInt("water_glasses", 0).apply()

    val currentMap = _routines.value.toMutableMap()
    currentMap.forEach { (day, list) ->
      currentMap[day] = list.map { ex ->
        if (day == "sun" && ex.id == "su1") ex else ex.copy(isCompleted = false)
      }
    }
    _routines.value = currentMap
    saveRoutines(currentMap)
  }

  companion object {
    fun getTodayDayKey(): String {
      return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "sun"
        Calendar.MONDAY -> "mon"
        Calendar.TUESDAY -> "tue"
        Calendar.WEDNESDAY -> "wed"
        Calendar.THURSDAY -> "thu"
        Calendar.FRIDAY -> "fri"
        Calendar.SATURDAY -> "sat"
        else -> "mon"
      }
    }
  }
}
