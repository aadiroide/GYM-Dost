package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GymDostRepository
import com.example.model.ActivityLevel
import com.example.model.ExerciseItem
import com.example.model.FitnessGoal
import com.example.model.IntakeItem
import com.example.model.QuickFoodItem
import com.example.model.TargetTimeline
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AppTab(val title: String, val icon: String) {
  DASHBOARD("Dashboard", "💪"),
  INTAKE("Aaj Kya Khaya", "🍽️"),
  WORKOUT("Workout", "🏋️"),
  DIET("Diet & Search", "🥑"),
  INFO("Architecture", "⚙️")
}

data class DashboardUiState(
  val userProfile: UserProfile = UserProfile(),
  val intakeLogs: List<IntakeItem> = emptyList(),
  val routines: Map<String, List<ExerciseItem>> = emptyMap(),
  val waterGlasses: Int = 0,
  val selectedDayKey: String = GymDostRepository.getTodayDayKey(),
  val currentTab: AppTab = AppTab.DASHBOARD,
  val caloriesEaten: Int = 0,
  val proteinEaten: Int = 0,
  val caloriesRemaining: Int = 2550,
  val proteinRemaining: Int = 145,
  val caloriesProgress: Float = 0f,
  val proteinProgress: Float = 0f,
  val workoutDoneCount: Int = 0,
  val workoutTotalCount: Int = 6,
  val workoutProgress: Float = 0f,
  val waterLiters: Float = 0f,
  val waterProgress: Float = 0f,
  val statusMessage: String = "Track active for today",
  val isSunday: Boolean = false,
  val toastMessage: String? = null
)

class GymDostViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = GymDostRepository(application)

  private val _selectedDayKey = MutableStateFlow(GymDostRepository.getTodayDayKey())
  val selectedDayKey: StateFlow<String> = _selectedDayKey.asStateFlow()

  private val _currentTab = MutableStateFlow(AppTab.DASHBOARD)
  val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  // Dialog Controls
  val showProfileDialog = MutableStateFlow(false)
  val showLogFoodDialog = MutableStateFlow(false)
  val showAddExerciseDialog = MutableStateFlow(false)

  val prefillFoodName = MutableStateFlow("")
  val prefillCalories = MutableStateFlow("")
  val prefillProtein = MutableStateFlow("")

  val searchQuery = MutableStateFlow("")

  private val coreMetricsFlow = combine(
    repository.userProfile,
    repository.intakeLogs,
    repository.routines,
    repository.waterGlasses,
    _selectedDayKey
  ) { profile, intake, routinesMap, water, dayKey ->
    val calEaten = intake.sumOf { it.calories }
    val protEaten = intake.sumOf { it.protein }

    val targetCals = profile.calculatedCalories
    val targetProt = profile.calculatedProtein
    val targetWater = profile.calculatedWaterLiters

    val calRemaining = targetCals - calEaten
    val protRemaining = targetProt - protEaten

    val calProgress = (calEaten.toFloat() / targetCals.coerceAtLeast(1)).coerceIn(0f, 1f)
    val protProgress = (protEaten.toFloat() / targetProt.coerceAtLeast(1)).coerceIn(0f, 1f)

    val currentDayExercises = routinesMap[dayKey] ?: emptyList()
    val doneExercises = currentDayExercises.count { it.isCompleted }
    val totalExercises = currentDayExercises.size
    val workoutProgress = if (totalExercises > 0) doneExercises.toFloat() / totalExercises else 0f

    val waterLiters = water * 0.25f
    val waterProgress = (waterLiters / targetWater.coerceAtLeast(1f)).coerceIn(0f, 1f)

    val statusMsg = when {
      protProgress >= 1f && calProgress >= 0.9f -> "🌟 Excellent! Aaj ka nutrition complete!"
      protRemaining > 0 -> "⚡ Abhi ${protRemaining}g Protein aur ${calRemaining.coerceAtLeast(0)} kcal baaki hai"
      else -> "Target ache se manage ho raha hai!"
    }

    DashboardUiState(
      userProfile = profile,
      intakeLogs = intake,
      routines = routinesMap,
      waterGlasses = water,
      selectedDayKey = dayKey,
      caloriesEaten = calEaten,
      proteinEaten = protEaten,
      caloriesRemaining = calRemaining,
      proteinRemaining = protRemaining,
      caloriesProgress = calProgress,
      proteinProgress = protProgress,
      workoutDoneCount = doneExercises,
      workoutTotalCount = totalExercises,
      workoutProgress = workoutProgress,
      waterLiters = waterLiters,
      waterProgress = waterProgress,
      statusMessage = statusMsg,
      isSunday = (dayKey == "sun")
    )
  }

  val uiState: StateFlow<DashboardUiState> = combine(
    coreMetricsFlow,
    _currentTab,
    _toastMessage
  ) { core, tab, toast ->
    core.copy(currentTab = tab, toastMessage = toast)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = DashboardUiState()
  )

  init {
    // Show profile dialog on very first launch if not yet configured
    if (!repository.userProfile.value.isConfigured) {
      showProfileDialog.value = true
    }
  }

  fun setTab(tab: AppTab) {
    _currentTab.value = tab
  }

  fun setWorkoutDay(dayKey: String) {
    _selectedDayKey.value = dayKey
  }

  fun addWater() {
    repository.addWaterGlass()
    showToast("💧 Water logged (+250ml)!")
  }

  fun logQuickSnack(snack: QuickFoodItem) {
    val item = IntakeItem(
      id = "intake_" + System.currentTimeMillis(),
      name = snack.name,
      calories = snack.calories,
      protein = snack.protein,
      mealTiming = "Quick Snack",
      emoji = snack.emoji,
      timestamp = System.currentTimeMillis()
    )
    repository.addIntakeItem(item)
    showToast("${snack.emoji} ${snack.name} added to intake!")
  }

  fun logCustomFood(name: String, calories: Int, protein: Int, timing: String) {
    if (name.isBlank()) return
    val item = IntakeItem(
      id = "intake_" + System.currentTimeMillis(),
      name = name.trim(),
      calories = calories,
      protein = protein,
      mealTiming = timing,
      emoji = "🍽️",
      timestamp = System.currentTimeMillis()
    )
    repository.addIntakeItem(item)
    showLogFoodDialog.value = false
    showToast("✅ ${name.trim()} added to your intake!")
  }

  fun removeIntakeItem(id: String) {
    repository.removeIntakeItem(id)
    showToast("🗑️ Item removed from intake")
  }

  fun toggleExercise(dayKey: String, exerciseId: String) {
    repository.toggleExercise(dayKey, exerciseId)
    val dayList = repository.routines.value[dayKey] ?: emptyList()
    val ex = dayList.firstOrNull { it.id == exerciseId }
    if (ex != null && !ex.isCompleted) {
      showToast("💪 Set finished!")
    }
  }

  fun addCustomExercise(dayKey: String, name: String, targetMuscle: String, setsAndReps: String) {
    if (name.isBlank()) return
    repository.addCustomExercise(dayKey, name.trim(), targetMuscle.trim(), setsAndReps.trim())
    showAddExerciseDialog.value = false
    showToast("🏋️ Added exercise to current routine!")
  }

  fun removeExercise(dayKey: String, exerciseId: String) {
    repository.removeExercise(dayKey, exerciseId)
    showToast("🗑️ Exercise removed")
  }

  fun resetToday() {
    repository.resetToday()
    showToast("🔄 Aaj ka tracker reset ho gaya!")
  }

  fun saveProfile(
    name: String,
    gender: String,
    age: Int,
    weight: Float,
    height: Float,
    goal: FitnessGoal,
    timeline: TargetTimeline,
    activity: ActivityLevel
  ) {
    val newProfile = UserProfile(
      name = name.ifBlank { "Rahul Sharma" },
      gender = gender,
      age = age.coerceIn(14, 80),
      weightKg = weight.coerceIn(30f, 250f),
      heightCm = height.coerceIn(100f, 230f),
      goal = goal,
      timeline = timeline,
      activityLevel = activity,
      isConfigured = true
    )
    repository.saveProfile(newProfile)
    showProfileDialog.value = false
    showToast("🎯 Profile & targets optimized!")
  }

  fun openLogFoodDialog(prefillName: String = "", prefillCals: Int = 0, prefillProt: Int = 0) {
    prefillFoodName.value = prefillName
    prefillCalories.value = if (prefillCals > 0) prefillCals.toString() else ""
    prefillProtein.value = if (prefillProt > 0) prefillProt.toString() else ""
    showLogFoodDialog.value = true
  }

  fun clearToast() {
    _toastMessage.value = null
  }

  private fun showToast(msg: String) {
    _toastMessage.value = msg
  }
}
