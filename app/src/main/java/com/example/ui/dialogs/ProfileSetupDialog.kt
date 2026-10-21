package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.ActivityLevel
import com.example.model.FitnessGoal
import com.example.model.TargetTimeline
import com.example.model.UserProfile
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.GymDarkSurface
import com.example.ui.theme.GymDarkSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupDialog(
  currentProfile: UserProfile,
  onDismiss: () -> Unit,
  onSave: (
    name: String,
    gender: String,
    age: Int,
    weight: Float,
    height: Float,
    goal: FitnessGoal,
    timeline: TargetTimeline,
    activity: ActivityLevel
  ) -> Unit
) {
  var name by remember { mutableStateOf(currentProfile.name) }
  var gender by remember { mutableStateOf(currentProfile.gender) }
  var ageText by remember { mutableStateOf(currentProfile.age.toString()) }
  var weightText by remember { mutableStateOf(currentProfile.weightKg.toString()) }
  var heightText by remember { mutableStateOf(currentProfile.heightCm.toInt().toString()) }

  var selectedGoal by remember { mutableStateOf(currentProfile.goal) }
  var selectedTimeline by remember { mutableStateOf(currentProfile.timeline) }
  var selectedActivity by remember { mutableStateOf(currentProfile.activityLevel) }

  var goalExpanded by remember { mutableStateOf(false) }
  var timelineExpanded by remember { mutableStateOf(false) }
  var activityExpanded by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = GymDarkSurface,
      border = androidx.compose.foundation.BorderStroke(1.dp, GymBorder),
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .testTag("dialog_profile_setup")
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "💪", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "GYM Dost Profile Setup",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "Personalize workout & diet goals",
                fontSize = 11.sp,
                color = TextSecondary
              )
            }
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
            Text(text = "✕", color = TextSecondary, fontSize = 16.sp)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Name
        Text(text = "Aapka Naam (Your Name) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          placeholder = { Text("e.g. Rahul Sharma", fontSize = 12.sp, color = Color.Gray) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_profile_name"),
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

        Spacer(modifier = Modifier.height(12.dp))

        // Gender & Age Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Gender Selector
          Column(modifier = Modifier.weight(1f)) {
            Text(text = "Gender", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(GymDarkSurfaceVariant, RoundedCornerShape(12.dp))
                .border(1.dp, GymBorder, RoundedCornerShape(12.dp))
                .padding(4.dp),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              listOf("male" to "Male 👨", "female" to "Female 👩").forEach { (gVal, gLabel) ->
                val isSel = gender == gVal
                Box(
                  modifier = Modifier
                    .weight(1f)
                    .background(
                      if (isSel) Emerald400 else Color.Transparent,
                      RoundedCornerShape(8.dp)
                    )
                    .clickable { gender = gVal }
                    .padding(vertical = 8.dp),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = gLabel,
                    fontSize = 11.sp,
                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSel) Color(0xFF022C22) else TextSecondary
                  )
                }
              }
            }
          }

          // Age
          Column(modifier = Modifier.weight(0.7f)) {
            Text(text = "Age (Years) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
              value = ageText,
              onValueChange = { ageText = it.filter { ch -> ch.isDigit() } },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_profile_age"),
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

        Spacer(modifier = Modifier.height(12.dp))

        // Weight & Height Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(text = "Weight (kg) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
              value = weightText,
              onValueChange = { weightText = it },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_profile_weight"),
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
            Text(text = "Height (cm) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
              value = heightText,
              onValueChange = { heightText = it.filter { ch -> ch.isDigit() } },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_profile_height"),
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

        Spacer(modifier = Modifier.height(12.dp))

        // Fitness Goal Dropdown
        Text(text = "Fitness Goal *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        ExposedDropdownMenuBox(
          expanded = goalExpanded,
          onExpandedChange = { goalExpanded = !goalExpanded }
        ) {
          OutlinedTextField(
            value = selectedGoal.title,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = goalExpanded) },
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
            expanded = goalExpanded,
            onDismissRequest = { goalExpanded = false },
            modifier = Modifier.background(GymDarkSurfaceVariant)
          ) {
            FitnessGoal.values().forEach { goal ->
              DropdownMenuItem(
                text = {
                  Column {
                    Text(text = goal.title, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 12.sp)
                    Text(text = goal.description, color = TextSecondary, fontSize = 10.sp)
                  }
                },
                onClick = {
                  selectedGoal = goal
                  goalExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Timeline Dropdown
        Text(text = "Target Duration (Timeframe) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        ExposedDropdownMenuBox(
          expanded = timelineExpanded,
          onExpandedChange = { timelineExpanded = !timelineExpanded }
        ) {
          OutlinedTextField(
            value = "${selectedTimeline.title} - ${selectedTimeline.subtitle}",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = timelineExpanded) },
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
            expanded = timelineExpanded,
            onDismissRequest = { timelineExpanded = false },
            modifier = Modifier.background(GymDarkSurfaceVariant)
          ) {
            TargetTimeline.values().forEach { tm ->
              DropdownMenuItem(
                text = {
                  Column {
                    Text(text = tm.title, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 12.sp)
                    Text(text = tm.subtitle, color = TextSecondary, fontSize = 10.sp)
                  }
                },
                onClick = {
                  selectedTimeline = tm
                  timelineExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Activity Level Dropdown
        Text(text = "Daily Activity Level", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        ExposedDropdownMenuBox(
          expanded = activityExpanded,
          onExpandedChange = { activityExpanded = !activityExpanded }
        ) {
          OutlinedTextField(
            value = selectedActivity.title,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = activityExpanded) },
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
            expanded = activityExpanded,
            onDismissRequest = { activityExpanded = false },
            modifier = Modifier.background(GymDarkSurfaceVariant)
          ) {
            ActivityLevel.values().forEach { act ->
              DropdownMenuItem(
                text = { Text(text = act.title, color = TextPrimary, fontSize = 12.sp) },
                onClick = {
                  selectedActivity = act
                  activityExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Submit Button
        Button(
          onClick = {
            val a = ageText.toIntOrNull() ?: currentProfile.age
            val w = weightText.toFloatOrNull() ?: currentProfile.weightKg
            val h = heightText.toFloatOrNull() ?: currentProfile.heightCm
            onSave(name, gender, a, w, h, selectedGoal, selectedTimeline, selectedActivity)
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = Emerald400,
            contentColor = Color(0xFF022C22)
          ),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_save_profile")
        ) {
          Text(text = "Save & Optimize My Profile 🚀", fontWeight = FontWeight.Black, fontSize = 14.sp)
        }
      }
    }
  }
}
