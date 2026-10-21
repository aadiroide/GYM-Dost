package com.example.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserProfile
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.Sky400
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProfileBanner(
  userProfile: UserProfile,
  onEditProfile: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(
        brush = Brush.linearGradient(
          colors = listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF0F172A))
        ),
        shape = RoundedCornerShape(18.dp)
      )
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(18.dp))
      .padding(16.dp)
  ) {
    Column {
      // Badges
      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Namaste Dost 👋",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = Emerald400
        )

        Box(
          modifier = Modifier
            .background(Color(0x2210B981), RoundedCornerShape(100.dp))
            .border(0.8.dp, Color(0x5510B981), RoundedCornerShape(100.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text(
            text = userProfile.timeline.title + " Target",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFA7F3D0)
          )
        }

        Box(
          modifier = Modifier
            .background(Color(0x22F59E0B), RoundedCornerShape(100.dp))
            .border(0.8.dp, Color(0x55F59E0B), RoundedCornerShape(100.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text(
            text = "Sunday OFF 🛌",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Amber400
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // User name
      Text(
        text = userProfile.name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Black,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      // Goals & Target info
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "Goal: ",
          fontSize = 12.sp,
          color = TextSecondary
        )
        Text(
          text = userProfile.goal.title,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Amber400
        )
        Text(
          text = " | Target: ",
          fontSize = 12.sp,
          color = TextSecondary
        )
        Text(
          text = "${userProfile.calculatedCalories} kcal",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Text(
          text = " | Protein: ",
          fontSize = 12.sp,
          color = TextSecondary
        )
        Text(
          text = "${userProfile.calculatedProtein}g",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Sky400
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Metric pills (Weight, Height, BMI) + Edit button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          MetricBox(label = "Weight", value = "${userProfile.weightKg} kg")
          MetricBox(label = "Height", value = "${userProfile.heightCm.toInt()} cm")
          MetricBox(
            label = "BMI",
            value = "%.1f".format(userProfile.bmi),
            valueColor = Emerald400
          )
        }

        OutlinedButton(
          onClick = onEditProfile,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFF1E293B),
            contentColor = TextPrimary
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, GymBorder),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.testTag("btn_edit_profile")
        ) {
          Text(text = "✏️ Edit", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun MetricBox(
  label: String,
  value: String,
  valueColor: Color = TextPrimary
) {
  Box(
    modifier = Modifier
      .background(Color(0xFF030712), RoundedCornerShape(10.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(10.dp))
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = label,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = TextSecondary
      )
      Text(
        text = value,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = valueColor
      )
    }
  }
}
