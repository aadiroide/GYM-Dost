package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.GymDarkSurface
import com.example.ui.theme.GymDarkSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogFoodDialog(
  initialName: String = "",
  initialCalories: String = "",
  initialProtein: String = "",
  onDismiss: () -> Unit,
  onSave: (name: String, calories: Int, protein: Int, timing: String) -> Unit
) {
  var name by remember { mutableStateOf(initialName) }
  var caloriesText by remember { mutableStateOf(initialCalories) }
  var proteinText by remember { mutableStateOf(initialProtein) }

  val timings = listOf(
    "Morning / Breakfast",
    "Mid-Day Snack",
    "Lunch",
    "Pre-Workout",
    "Post-Workout",
    "Evening Snack",
    "Dinner",
    "Bedtime"
  )
  var selectedTiming by remember { mutableStateOf(timings[0]) }
  var timingExpanded by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = GymDarkSurface,
      border = androidx.compose.foundation.BorderStroke(1.dp, GymBorder),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("dialog_log_food")
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Log Food (Aaj Kya Khaya)",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "Jo bhi khaya use enter karein",
              fontSize = 11.sp,
              color = TextSecondary
            )
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
            Text(text = "✕", color = TextSecondary, fontSize = 16.sp)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Food Name
        Text(text = "Food Item Name *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          placeholder = { Text("e.g. 100g Raw Peanuts", fontSize = 12.sp, color = Color.Gray) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_food_name"),
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

        Spacer(modifier = Modifier.height(10.dp))

        // Calories & Protein Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(text = "Calories (kcal) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
              value = caloriesText,
              onValueChange = { caloriesText = it.filter { ch -> ch.isDigit() } },
              placeholder = { Text("567", fontSize = 12.sp, color = Color.Gray) },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_food_calories"),
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
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(text = "Protein (g) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
              value = proteinText,
              onValueChange = { proteinText = it.filter { ch -> ch.isDigit() } },
              placeholder = { Text("26", fontSize = 12.sp, color = Color.Gray) },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_food_protein"),
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
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Timing Dropdown
        Text(text = "Time / Meal Timing", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        ExposedDropdownMenuBox(
          expanded = timingExpanded,
          onExpandedChange = { timingExpanded = !timingExpanded }
        ) {
          OutlinedTextField(
            value = selectedTiming,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = timingExpanded) },
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
            expanded = timingExpanded,
            onDismissRequest = { timingExpanded = false },
            modifier = Modifier.background(GymDarkSurfaceVariant)
          ) {
            timings.forEach { t ->
              DropdownMenuItem(
                text = { Text(text = t, color = TextPrimary, fontSize = 12.sp) },
                onClick = {
                  selectedTiming = t
                  timingExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = {
            if (name.isNotBlank()) {
              val c = caloriesText.toIntOrNull() ?: 0
              val p = proteinText.toIntOrNull() ?: 0
              onSave(name, c, p, selectedTiming)
            }
          },
          enabled = name.isNotBlank(),
          colors = ButtonDefaults.buttonColors(
            containerColor = Emerald400,
            contentColor = Color(0xFF022C22)
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("btn_submit_food_log")
        ) {
          Text(text = "Add to Today's Intake", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
      }
    }
  }
}
