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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ExerciseItem
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.GymDarkSurface
import com.example.ui.theme.GymDarkSurfaceVariant
import com.example.ui.theme.Rose500
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
  routines: Map<String, List<ExerciseItem>>,
  selectedDayKey: String,
  onSelectDay: (String) -> Unit,
  onToggleExercise: (dayKey: String, id: String) -> Unit,
  onOpenAddExercise: () -> Unit,
  onRemoveExercise: (dayKey: String, id: String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  val dayLabels = listOf(
    "mon" to "Mon: Push (Chest/Shoulders/Triceps)",
    "tue" to "Tue: Pull (Back/Biceps/Delts)",
    "wed" to "Wed: Legs & Core (Squats/Hamstrings)",
    "thu" to "Thu: Push & Hypertrophy",
    "fri" to "Fri: Pull & Thickness",
    "sat" to "Sat: Legs & Calves/Abs",
    "sun" to "Sun: Rest Day (SUNDAY OFF) 🛌"
  )

  val currentDayLabel = dayLabels.firstOrNull { it.first == selectedDayKey }?.second ?: "Workout Day"
  val exerciseList = routines[selectedDayKey] ?: emptyList()
  var dayMenuExpanded by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🏋️", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Weekly Routine (Sunday OFF)",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }
          Text(
            text = "Optimized for your fitness goal & timeline",
            fontSize = 11.sp,
            color = TextSecondary
          )
        }
      }
    }

    // Day Selector Chips (Mon - Sun)
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(vertical = 2.dp)
      ) {
        items(listOf("mon" to "Mon", "tue" to "Tue", "wed" to "Wed", "thu" to "Thu", "fri" to "Fri", "sat" to "Sat", "sun" to "Sun 🛌")) { (dKey, dName) ->
          val isSelected = selectedDayKey == dKey
          Box(
            modifier = Modifier
              .background(
                if (isSelected) Emerald400 else Color(0xFF0F172A),
                RoundedCornerShape(10.dp)
              )
              .border(
                1.dp,
                if (isSelected) Emerald400 else GymBorder,
                RoundedCornerShape(10.dp)
              )
              .clickable { onSelectDay(dKey) }
              .padding(horizontal = 12.dp, vertical = 7.dp)
              .testTag("chip_day_$dKey")
          ) {
            Text(
              text = dName,
              fontSize = 12.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) Color(0xFF022C22) else TextSecondary
            )
          }
        }
      }
    }

    // Selected Day Full Dropdown selector
    item {
      ExposedDropdownMenuBox(
        expanded = dayMenuExpanded,
        onExpandedChange = { dayMenuExpanded = !dayMenuExpanded }
      ) {
        OutlinedTextField(
          value = currentDayLabel,
          onValueChange = {},
          readOnly = true,
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dayMenuExpanded) },
          modifier = Modifier
            .fillMaxWidth()
            .menuAnchor(),
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = Emerald400,
            unfocusedBorderColor = GymBorder,
            focusedContainerColor = GymDarkSurfaceVariant,
            unfocusedContainerColor = GymDarkSurfaceVariant
          ),
          shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(
          expanded = dayMenuExpanded,
          onDismissRequest = { dayMenuExpanded = false },
          modifier = Modifier.background(GymDarkSurfaceVariant)
        ) {
          dayLabels.forEach { (key, label) ->
            DropdownMenuItem(
              text = { Text(text = label, color = TextPrimary, fontSize = 12.sp) },
              onClick = {
                onSelectDay(key)
                dayMenuExpanded = false
              }
            )
          }
        }
      }
    }

    // Sunday OFF Banner
    if (selectedDayKey == "sun") {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x22F59E0B), RoundedCornerShape(14.dp))
            .border(1.dp, Color(0x44F59E0B), RoundedCornerShape(14.dp))
            .padding(14.dp)
        ) {
          Column {
            Text(
              text = "🛌 Sunday Rest & Recovery Day!",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = Amber400
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "No heavy gym workout today. Apne muscles ko heal hone dein, 3.5L water complete karein aur protein intake maintain rakhein. Light walking aur body stretching kar sakte hain.",
              fontSize = 11.sp,
              color = Color(0xFFFDE68A),
              lineHeight = 16.sp
            )
          }
        }
      }
    }

    // Exercise list items
    items(exerciseList, key = { it.id }) { ex ->
      ExerciseCard(
        exercise = ex,
        onToggle = { onToggleExercise(selectedDayKey, ex.id) },
        onWatchGuide = {
          val query = URLEncoder.encode("${ex.name} proper form gym exercise tutorial", StandardCharsets.UTF_8.toString())
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$query"))
          context.startActivity(intent)
        },
        onDelete = { onRemoveExercise(selectedDayKey, ex.id) }
      )
    }

    // Add Custom Exercise Trigger
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0x1110B981), RoundedCornerShape(14.dp))
          .border(1.dp, Color(0x3310B981), RoundedCornerShape(14.dp))
          .clickable { onOpenAddExercise() }
          .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "➕", fontSize = 13.sp)
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Add Custom Exercise to Current Day",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Emerald400
          )
        }
      }
    }
  }
}

@Composable
fun ExerciseCard(
  exercise: ExerciseItem,
  onToggle: () -> Unit,
  onWatchGuide: () -> Unit,
  onDelete: () -> Unit
) {
  val isDone = exercise.isCompleted

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        if (isDone) Color(0xFF070D18) else Color(0xFF0F172A),
        RoundedCornerShape(14.dp)
      )
      .border(
        1.dp,
        if (isDone) Color(0x2210B981) else Color(0xFF334155),
        RoundedCornerShape(14.dp)
      )
      .padding(horizontal = 12.dp, vertical = 10.dp)
      .testTag("card_exercise_${exercise.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Checkbox(
          checked = isDone,
          onCheckedChange = { onToggle() },
          colors = CheckboxDefaults.colors(
            checkedColor = Emerald400,
            uncheckedColor = TextSecondary,
            checkmarkColor = Color(0xFF022C22)
          )
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column {
          Text(
            text = exercise.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDone) TextMuted else TextPrimary,
            textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
          )
          Text(
            text = exercise.targetMuscle,
            fontSize = 10.sp,
            color = TextSecondary
          )
        }
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // YouTube Guide Button
        Box(
          modifier = Modifier
            .background(Color(0x22EF4444), RoundedCornerShape(8.dp))
            .border(0.8.dp, Color(0x55EF4444), RoundedCornerShape(8.dp))
            .clickable { onWatchGuide() }
            .padding(horizontal = 7.dp, vertical = 4.dp)
            .testTag("btn_yt_guide_${exercise.id}")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "▶️", fontSize = 10.sp)
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = "Guide",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFFCA5A5)
            )
          }
        }

        // Sets & Reps Pill
        Box(
          modifier = Modifier
            .background(Color(0x2210B981), RoundedCornerShape(8.dp))
            .border(0.8.dp, Color(0x4410B981), RoundedCornerShape(8.dp))
            .padding(horizontal = 7.dp, vertical = 4.dp)
        ) {
          Text(
            text = exercise.setsAndReps,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Emerald400
          )
        }

        // Delete button
        IconButton(
          onClick = onDelete,
          modifier = Modifier.size(26.dp)
        ) {
          Text(text = "✕", color = Color(0xFF64748B), fontSize = 13.sp)
        }
      }
    }
  }
}
