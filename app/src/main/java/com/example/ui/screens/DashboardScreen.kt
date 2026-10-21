package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GymDostData
import com.example.model.ExerciseItem
import com.example.model.IntakeItem
import com.example.model.QuickFoodItem
import com.example.model.UserProfile
import com.example.ui.components.NutritionBalanceCards
import com.example.ui.components.ProfileBanner
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Sky400
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DashboardScreen(
  userProfile: UserProfile,
  caloriesEaten: Int,
  caloriesTarget: Int,
  caloriesRemaining: Int,
  caloriesProgress: Float,
  proteinEaten: Int,
  proteinTarget: Int,
  proteinRemaining: Int,
  proteinProgress: Float,
  workoutDone: Int,
  workoutTotal: Int,
  workoutDayName: String,
  workoutProgress: Float,
  waterGlasses: Int,
  waterLiters: Float,
  waterTarget: Float,
  waterProgress: Float,
  statusMessage: String,
  intakeLogs: List<IntakeItem>,
  todayExercises: List<ExerciseItem>,
  selectedDayKey: String,
  onEditProfile: () -> Unit,
  onAddWater: () -> Unit,
  onAddQuickSnack: (QuickFoodItem) -> Unit,
  onOpenLogCustomFood: () -> Unit,
  onToggleExercise: (String) -> Unit,
  onViewAllIntake: () -> Unit,
  onViewAllWorkouts: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Personalized User Welcome Banner
    item {
      ProfileBanner(
        userProfile = userProfile,
        onEditProfile = onEditProfile
      )
    }

    // 2. Live Nutrition Balance & Macro HUD Cards
    item {
      NutritionBalanceCards(
        caloriesEaten = caloriesEaten,
        caloriesTarget = caloriesTarget,
        caloriesRemaining = caloriesRemaining,
        caloriesProgress = caloriesProgress,
        proteinEaten = proteinEaten,
        proteinTarget = proteinTarget,
        proteinRemaining = proteinRemaining,
        proteinProgress = proteinProgress,
        workoutDone = workoutDone,
        workoutTotal = workoutTotal,
        workoutDayName = workoutDayName,
        workoutProgress = workoutProgress,
        waterGlasses = waterGlasses,
        waterLiters = waterLiters,
        waterTarget = waterTarget,
        waterProgress = waterProgress,
        statusMessage = statusMessage,
        onAddWater = onAddWater
      )
    }

    // 3. 1-Click Quick Add Snacks Row
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF0F172A), RoundedCornerShape(16.dp))
          .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
          .padding(14.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "⚡ 1-Click Quick Add (Peanuts, Eggs, Whey...)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFCBD5E1)
            )
            Text(
              text = "+ Log Khana",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Emerald400,
              modifier = Modifier.clickable { onOpenLogCustomFood() }
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 2.dp)
          ) {
            items(GymDostData.QUICK_SNACKS) { snack ->
              QuickSnackChip(snack = snack, onClick = { onAddQuickSnack(snack) })
            }
          }
        }
      }
    }

    // 4. Aaj Kya Khaya (Recent Log Preview)
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF0F172A), RoundedCornerShape(16.dp))
          .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
          .padding(14.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "🍽️", fontSize = 14.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Aaj Maine Kya Khaya",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            }
            Text(
              text = "View All (${intakeLogs.size}) →",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Sky400,
              modifier = Modifier.clickable { onViewAllIntake() }
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          if (intakeLogs.isEmpty()) {
            Text(
              text = "Aapne aaj abhi tak kuch log nahi kiya hai. Quick snacks par tap karein ya + Log Food dabayein!",
              fontSize = 11.sp,
              color = TextSecondary,
              lineHeight = 15.sp
            )
          } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              intakeLogs.take(3).forEach { item ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF030712), RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = item.emoji, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = item.name,
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Medium,
                      color = TextPrimary
                    )
                  }
                  Text(
                    text = "+${item.protein}g P • ${item.calories}k",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo400
                  )
                }
              }
            }
          }
        }
      }
    }

    // 5. Today's Workout Quick Checklist
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF0F172A), RoundedCornerShape(16.dp))
          .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
          .padding(14.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "🏋️", fontSize = 14.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Today's Routine ($workoutDayName)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            }
            Text(
              text = "Full Split →",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Emerald400,
              modifier = Modifier.clickable { onViewAllWorkouts() }
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          if (selectedDayKey == "sun") {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x22F59E0B), RoundedCornerShape(10.dp))
                .border(1.dp, Color(0x44F59E0B), RoundedCornerShape(10.dp))
                .padding(10.dp)
            ) {
              Text(
                text = "🛌 Sunday Rest & Recovery Day! No heavy gym workout today. Muscles heal hone dein.",
                fontSize = 11.sp,
                color = Amber400
              )
            }
          } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              todayExercises.take(4).forEach { ex ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF030712), RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Checkbox(
                      checked = ex.isCompleted,
                      onCheckedChange = { onToggleExercise(ex.id) },
                      colors = CheckboxDefaults.colors(
                        checkedColor = Emerald400,
                        uncheckedColor = TextSecondary,
                        checkmarkColor = Color(0xFF022C22)
                      ),
                      modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                      Text(
                        text = ex.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (ex.isCompleted) TextMuted else TextPrimary,
                        textDecoration = if (ex.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                      )
                      Text(
                        text = ex.setsAndReps,
                        fontSize = 9.sp,
                        color = TextSecondary
                      )
                    }
                  }

                  // YouTube Guide
                  Box(
                    modifier = Modifier
                      .background(Color(0x22EF4444), RoundedCornerShape(6.dp))
                      .clickable {
                        val query = URLEncoder.encode("${ex.name} proper form gym exercise tutorial", StandardCharsets.UTF_8.toString())
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$query"))
                        context.startActivity(intent)
                      }
                      .padding(horizontal = 6.dp, vertical = 3.dp)
                  ) {
                    Text(text = "▶️ Guide", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFCA5A5))
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
