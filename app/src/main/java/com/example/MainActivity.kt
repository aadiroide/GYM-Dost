package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GymDostData
import com.example.ui.components.GymBottomBar
import com.example.ui.components.GymHeader
import com.example.ui.dialogs.AddExerciseDialog
import com.example.ui.dialogs.LogFoodDialog
import com.example.ui.dialogs.ProfileSetupDialog
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DietScreen
import com.example.ui.screens.InfoScreen
import com.example.ui.screens.IntakeScreen
import com.example.ui.screens.WorkoutScreen
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymDarkBackground
import com.example.ui.theme.GymDostTheme
import com.example.ui.theme.Rose500
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.AppTab
import com.example.viewmodel.GymDostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

  private val viewModel: GymDostViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      GymDostTheme {
        GymDostApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun GymDostApp(viewModel: GymDostViewModel) {
  val uiState by viewModel.uiState.collectAsState()
  val showProfileDialog by viewModel.showProfileDialog.collectAsState()
  val showLogFoodDialog by viewModel.showLogFoodDialog.collectAsState()
  val showAddExerciseDialog by viewModel.showAddExerciseDialog.collectAsState()

  val prefillFoodName by viewModel.prefillFoodName.collectAsState()
  val prefillCalories by viewModel.prefillCalories.collectAsState()
  val prefillProtein by viewModel.prefillProtein.collectAsState()

  var showResetConfirmDialog by remember { mutableStateOf(false) }

  // Custom Toast Banner
  var toastText by remember { mutableStateOf<String?>(null) }
  LaunchedEffect(uiState.toastMessage) {
    uiState.toastMessage?.let { msg ->
      toastText = msg
      delay(2200)
      toastText = null
      viewModel.clearToast()
    }
  }

  // BackHandler: return to Dashboard if in any other sub-tab
  BackHandler(enabled = uiState.currentTab != AppTab.DASHBOARD) {
    viewModel.setTab(AppTab.DASHBOARD)
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = GymDarkBackground,
    topBar = {
      Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        GymHeader(
          userName = uiState.userProfile.name,
          onOpenProfile = { viewModel.showProfileDialog.value = true },
          onResetToday = { showResetConfirmDialog = true }
        )
      }
    },
    bottomBar = {
      GymBottomBar(
        currentTab = uiState.currentTab,
        onTabSelected = { viewModel.setTab(it) }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      // Screen Content based on current Tab
      when (uiState.currentTab) {
        AppTab.DASHBOARD -> {
          val todayKey = GymDostData.INITIAL_ROUTINES.keys.firstOrNull { it == uiState.selectedDayKey } ?: "mon"
          val dayName = when (todayKey) {
            "mon" -> "Push Day"
            "tue" -> "Pull Day"
            "wed" -> "Legs Day"
            "thu" -> "Push 2"
            "fri" -> "Pull 2"
            "sat" -> "Legs 2"
            "sun" -> "Rest Day"
            else -> "Workout Day"
          }

          DashboardScreen(
            userProfile = uiState.userProfile,
            caloriesEaten = uiState.caloriesEaten,
            caloriesTarget = uiState.userProfile.calculatedCalories,
            caloriesRemaining = uiState.caloriesRemaining,
            caloriesProgress = uiState.caloriesProgress,
            proteinEaten = uiState.proteinEaten,
            proteinTarget = uiState.userProfile.calculatedProtein,
            proteinRemaining = uiState.proteinRemaining,
            proteinProgress = uiState.proteinProgress,
            workoutDone = uiState.workoutDoneCount,
            workoutTotal = uiState.workoutTotalCount,
            workoutDayName = dayName,
            workoutProgress = uiState.workoutProgress,
            waterGlasses = uiState.waterGlasses,
            waterLiters = uiState.waterLiters,
            waterTarget = uiState.userProfile.calculatedWaterLiters,
            waterProgress = uiState.waterProgress,
            statusMessage = uiState.statusMessage,
            intakeLogs = uiState.intakeLogs,
            todayExercises = uiState.routines[uiState.selectedDayKey] ?: emptyList(),
            selectedDayKey = uiState.selectedDayKey,
            onEditProfile = { viewModel.showProfileDialog.value = true },
            onAddWater = { viewModel.addWater() },
            onAddQuickSnack = { viewModel.logQuickSnack(it) },
            onOpenLogCustomFood = { viewModel.openLogFoodDialog() },
            onToggleExercise = { viewModel.toggleExercise(uiState.selectedDayKey, it) },
            onViewAllIntake = { viewModel.setTab(AppTab.INTAKE) },
            onViewAllWorkouts = { viewModel.setTab(AppTab.WORKOUT) }
          )
        }

        AppTab.INTAKE -> {
          IntakeScreen(
            intakeLogs = uiState.intakeLogs,
            onAddQuickSnack = { viewModel.logQuickSnack(it) },
            onOpenLogCustomFood = { viewModel.openLogFoodDialog() },
            onRemoveIntake = { viewModel.removeIntakeItem(it) }
          )
        }

        AppTab.WORKOUT -> {
          WorkoutScreen(
            routines = uiState.routines,
            selectedDayKey = uiState.selectedDayKey,
            onSelectDay = { viewModel.setWorkoutDay(it) },
            onToggleExercise = { dKey, id -> viewModel.toggleExercise(dKey, id) },
            onOpenAddExercise = { viewModel.showAddExerciseDialog.value = true },
            onRemoveExercise = { dKey, id -> viewModel.removeExercise(dKey, id) }
          )
        }

        AppTab.DIET -> {
          DietScreen(
            onLogMeal = { name, cals, prot, emoji ->
              viewModel.logCustomFood(name, cals, prot, "Diet Plan")
            },
            onOpenLogCustomFood = { name, cals, prot ->
              viewModel.openLogFoodDialog(name, cals, prot)
            }
          )
        }

        AppTab.INFO -> {
          InfoScreen()
        }
      }

      // Snappy Floating Toast Notification at bottom
      AnimatedVisibility(
        visible = toastText != null,
        enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 20.dp)
      ) {
        toastText?.let { msg ->
          Box(
            modifier = Modifier
              .background(Color(0xFF0F172A), RoundedCornerShape(12.dp))
              .border(1.dp, Color(0xFF334155), RoundedCornerShape(12.dp))
              .padding(horizontal = 16.dp, vertical = 10.dp)
          ) {
            Text(
              text = msg,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }
        }
      }
    }
  }

  // Reset Today Confirmation Dialog
  if (showResetConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showResetConfirmDialog = false },
      title = { Text(text = "Reset Today?", fontWeight = FontWeight.Bold, color = TextPrimary) },
      text = {
        Text(
          text = "Kya aap aaj ka khana (intake log) aur workout checks reset karna chahte hain? Sabhi remaining targets 100% bachi hui show karenge.",
          fontSize = 12.sp,
          color = TextSecondary
        )
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.resetToday()
            showResetConfirmDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = Rose500)
        ) {
          Text("Haan, Reset Karein", fontWeight = FontWeight.Bold, color = Color.White)
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showResetConfirmDialog = false }) {
          Text("Cancel", color = TextSecondary)
        }
      },
      containerColor = Color(0xFF0F172A),
      shape = RoundedCornerShape(18.dp)
    )
  }

  // Profile Setup / Edit Dialog
  if (showProfileDialog) {
    ProfileSetupDialog(
      currentProfile = uiState.userProfile,
      onDismiss = { viewModel.showProfileDialog.value = false },
      onSave = { name, gender, age, weight, height, goal, timeline, activity ->
        viewModel.saveProfile(name, gender, age, weight, height, goal, timeline, activity)
      }
    )
  }

  // Custom Log Food Dialog
  if (showLogFoodDialog) {
    LogFoodDialog(
      initialName = prefillFoodName,
      initialCalories = prefillCalories,
      initialProtein = prefillProtein,
      onDismiss = { viewModel.showLogFoodDialog.value = false },
      onSave = { name, cals, prot, timing ->
        viewModel.logCustomFood(name, cals, prot, timing)
      }
    )
  }

  // Add Custom Exercise Dialog
  if (showAddExerciseDialog) {
    val dayName = when (uiState.selectedDayKey) {
      "mon" -> "Monday (Push)"
      "tue" -> "Tuesday (Pull)"
      "wed" -> "Wednesday (Legs)"
      "thu" -> "Thursday (Push 2)"
      "fri" -> "Friday (Pull 2)"
      "sat" -> "Saturday (Legs 2)"
      "sun" -> "Sunday"
      else -> "Current Day"
    }
    AddExerciseDialog(
      dayName = dayName,
      onDismiss = { viewModel.showAddExerciseDialog.value = false },
      onSave = { name, target, sets ->
        viewModel.addCustomExercise(uiState.selectedDayKey, name, target, sets)
      }
    )
  }
}
