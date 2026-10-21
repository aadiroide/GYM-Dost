package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.GymDarkSurface
import com.example.ui.theme.Rose500
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun GymHeader(
  userName: String,
  onOpenProfile: () -> Unit,
  onResetToday: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(GymDarkSurface)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // App Logo + Name
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .background(
            brush = Brush.linearGradient(
              listOf(Color(0xFF10B981), Color(0xFF14B8A6), Color(0xFFFBBF24))
            ),
            shape = RoundedCornerShape(12.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "💪", fontSize = 22.sp)
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "GYM Dost",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.width(6.dp))
          Box(
            modifier = Modifier
              .background(
                Color(0x2210B981),
                shape = RoundedCornerShape(100.dp)
              )
              .border(0.8.dp, Color(0x5510B981), RoundedCornerShape(100.dp))
              .padding(horizontal = 7.dp, vertical = 2.dp)
          ) {
            Text(
              text = "FREE",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = Emerald400
            )
          }
        }
        Text(
          text = "Aapka Gym & Diet Saathi",
          fontSize = 11.sp,
          color = TextSecondary
        )
      }
    }

    // Actions: Profile & Reset
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedButton(
        onClick = onOpenProfile,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
          containerColor = Color(0xFF1E293B),
          contentColor = TextPrimary
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, GymBorder),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
        modifier = Modifier.testTag("btn_open_profile")
      ) {
        Text(text = "👤 Profile", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
      }

      OutlinedButton(
        onClick = onResetToday,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
          containerColor = Color(0x1AF43F5E),
          contentColor = Rose500
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x44F43F5E)),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
        modifier = Modifier.testTag("btn_reset_today")
      ) {
        Text(text = "Reset", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
      }
    }
  }
}
