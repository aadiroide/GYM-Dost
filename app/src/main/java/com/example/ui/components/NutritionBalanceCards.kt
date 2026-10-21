package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Sky400
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun NutritionBalanceCards(
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
  onAddWater: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(Color(0xFF0F172A), RoundedCornerShape(18.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(18.dp))
      .padding(16.dp)
  ) {
    // Header & Status
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "⚡ Daily Nutrition & Remaining Balance",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Text(
          text = "Aap jo bhi khate hain wo yahan auto-adjust hota hai",
          fontSize = 11.sp,
          color = TextSecondary
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Status Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color(0x2210B981), RoundedCornerShape(10.dp))
        .border(1.dp, Color(0x4410B981), RoundedCornerShape(10.dp))
        .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
      Text(
        text = statusMessage,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = Emerald400
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Grid of 4 Cards: Top Row (Calories, Protein)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 1. Calories Card
      MacroCard(
        title = "Calories (kcal)",
        titleColor = Amber400,
        targetText = "/ $caloriesTarget",
        currentValue = "$caloriesEaten",
        remainingText = if (caloriesRemaining > 0) "$caloriesRemaining bachi" else "Met (+${-caloriesRemaining})",
        remainingColor = if (caloriesRemaining > 0) Amber400 else Emerald400,
        progress = caloriesProgress,
        progressColor = Amber400,
        subText = "${(caloriesProgress * 100).toInt()}% of $caloriesTarget kcal",
        modifier = Modifier.weight(1f)
      )

      // 2. Protein Card
      MacroCard(
        title = "Protein (g)",
        titleColor = Indigo400,
        targetText = "/ ${proteinTarget}g",
        currentValue = "${proteinEaten}g",
        remainingText = if (proteinRemaining > 0) "${proteinRemaining}g aur chahiye" else "Met (+${-proteinRemaining}g)",
        remainingColor = if (proteinRemaining > 0) Indigo400 else Emerald400,
        progress = proteinProgress,
        progressColor = Indigo400,
        subText = "${(proteinProgress * 100).toInt()}% complete",
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Bottom Row (Daily Workout, Water & Rest)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 3. Workout Routine Status
      MacroCard(
        title = "Daily Workout",
        titleColor = Emerald400,
        targetText = "${(workoutProgress * 100).toInt()}%",
        currentValue = "$workoutDone / $workoutTotal",
        remainingText = workoutDayName,
        remainingColor = TextSecondary,
        progress = workoutProgress,
        progressColor = Emerald400,
        subText = "Sunday: Rest Day",
        modifier = Modifier.weight(1f)
      )

      // 4. Water & Hydration Card with +1 Glass
      WaterCard(
        glasses = waterGlasses,
        liters = waterLiters,
        targetLiters = waterTarget,
        progress = waterProgress,
        onAddGlass = onAddWater,
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun MacroCard(
  title: String,
  titleColor: Color,
  targetText: String,
  currentValue: String,
  remainingText: String,
  remainingColor: Color,
  progress: Float,
  progressColor: Color,
  subText: String,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progress.coerceIn(0f, 1f),
    animationSpec = tween(500),
    label = "progress"
  )

  Box(
    modifier = modifier
      .background(Color(0xFF030712), RoundedCornerShape(14.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(14.dp))
      .padding(12.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = titleColor)
        Text(text = targetText, fontSize = 10.sp, color = TextSecondary)
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        Text(text = currentValue, fontSize = 18.sp, fontWeight = FontWeight.Black, color = TextPrimary)
        Text(text = remainingText, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = remainingColor)
      }

      Spacer(modifier = Modifier.height(8.dp))

      LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(100.dp)),
        color = progressColor,
        trackColor = Color(0xFF1E293B)
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(text = subText, fontSize = 9.sp, color = TextMuted)
    }
  }
}

@Composable
private fun WaterCard(
  glasses: Int,
  liters: Float,
  targetLiters: Float,
  progress: Float,
  onAddGlass: () -> Unit,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progress.coerceIn(0f, 1f),
    animationSpec = tween(500),
    label = "water_progress"
  )

  Box(
    modifier = modifier
      .background(Color(0xFF030712), RoundedCornerShape(14.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(14.dp))
      .padding(12.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "Water & Rest", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Sky400)
        Text(text = "%.1fL target".format(targetLiters), fontSize = 10.sp, color = Sky400)
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "$glasses Glass",
          fontSize = 17.sp,
          fontWeight = FontWeight.Black,
          color = TextPrimary
        )

        OutlinedButton(
          onClick = onAddGlass,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0x2238BDF8),
            contentColor = Sky400
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x4438BDF8)),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp, vertical = 2.dp),
          modifier = Modifier.testTag("btn_add_water_glass")
        ) {
          Text(text = "+1 Glass", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(100.dp)),
        color = Sky400,
        trackColor = Color(0xFF1E293B)
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "%.1f L logged today".format(liters),
        fontSize = 9.sp,
        color = TextMuted
      )
    }
  }
}
