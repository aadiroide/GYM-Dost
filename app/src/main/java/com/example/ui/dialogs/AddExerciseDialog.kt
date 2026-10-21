package com.example.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.GymDarkSurface
import com.example.ui.theme.GymDarkSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AddExerciseDialog(
  dayName: String,
  onDismiss: () -> Unit,
  onSave: (name: String, targetMuscle: String, setsAndReps: String) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var targetMuscle by remember { mutableStateOf("") }
  var setsAndReps by remember { mutableStateOf("3 Sets × 12 Reps") }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = GymDarkSurface,
      border = androidx.compose.foundation.BorderStroke(1.dp, GymBorder),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("dialog_add_exercise")
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Add Custom Workout",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "Adding to $dayName",
              fontSize = 11.sp,
              color = TextSecondary
            )
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
            Text(text = "✕", color = TextSecondary, fontSize = 16.sp)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(text = "Exercise Name *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          placeholder = { Text("e.g. Incline Cable Flyes", fontSize = 12.sp, color = Color.Gray) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_exercise_name"),
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

        Text(text = "Target Muscle Group", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = targetMuscle,
          onValueChange = { targetMuscle = it },
          placeholder = { Text("e.g. Upper Chest", fontSize = 12.sp, color = Color.Gray) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_exercise_target"),
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

        Text(text = "Sets & Reps", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = setsAndReps,
          onValueChange = { setsAndReps = it },
          placeholder = { Text("e.g. 3 Sets × 12 Reps", fontSize = 12.sp, color = Color.Gray) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_exercise_sets"),
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = {
            if (name.isNotBlank()) {
              onSave(
                name,
                targetMuscle.ifBlank { "General Hypertrophy" },
                setsAndReps.ifBlank { "3 Sets × 10 Reps" }
              )
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
            .testTag("btn_submit_exercise")
        ) {
          Text(text = "Save to Current Routine", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
      }
    }
  }
}
