package com.example.ui.screens

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GymDostData
import com.example.model.IntakeItem
import com.example.model.QuickFoodItem
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Rose500
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun IntakeScreen(
  intakeLogs: List<IntakeItem>,
  onAddQuickSnack: (QuickFoodItem) -> Unit,
  onOpenLogCustomFood: () -> Unit,
  onRemoveIntake: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header & Custom Log Trigger
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🍽️", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Aaj Maine Kya Khaya",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }
          Text(
            text = "Morning se Saam tak live meal & snack logger",
            fontSize = 11.sp,
            color = TextSecondary
          )
        }

        Button(
          onClick = onOpenLogCustomFood,
          colors = ButtonDefaults.buttonColors(
            containerColor = Emerald400,
            contentColor = Color(0xFF022C22)
          ),
          shape = RoundedCornerShape(10.dp),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.testTag("btn_log_custom_food")
        ) {
          Text(text = "➕ Log Food", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // 1-Click Quick Add Snacks Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF0F172A), RoundedCornerShape(16.dp))
          .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
          .padding(14.dp)
      ) {
        Column {
          Text(
            text = "⚡ 1-Click Quick Add Snacks (Peanuts, Eggs, Whey...):",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFCBD5E1)
          )
          Spacer(modifier = Modifier.height(8.dp))

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
          ) {
            items(GymDostData.QUICK_SNACKS) { snack ->
              QuickSnackChip(
                snack = snack,
                onClick = { onAddQuickSnack(snack) }
              )
            }
          }
        }
      }
    }

    // Logged items count indicator
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Logged Food Items Today",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextSecondary
        )
        Text(
          text = "${intakeLogs.size} items added",
          fontSize = 11.sp,
          color = TextMuted
        )
      }
    }

    // Empty state or list of logged items
    if (intakeLogs.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF030712), RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xFF334155), RoundedCornerShape(14.dp))
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "🥗", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Aapne aaj abhi tak kuch log nahi kiya hai.",
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              color = TextSecondary
            )
            Text(
              text = "Upar diye gaye quick snacks (jaise 100g Raw Peanuts ya Eggs) par tap karein ya + Log Food dabayein!",
              fontSize = 10.sp,
              color = TextMuted,
              modifier = Modifier.padding(top = 4.dp),
              lineHeight = 14.sp
            )
          }
        }
      }
    } else {
      items(intakeLogs, key = { it.id }) { item ->
        IntakeLogRow(
          item = item,
          onDelete = { onRemoveIntake(item.id) }
        )
      }
    }
  }
}

@Composable
fun QuickSnackChip(
  snack: QuickFoodItem,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .background(Color(0xFF030712), RoundedCornerShape(10.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(10.dp))
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 7.dp)
      .testTag("chip_snack_${snack.name}")
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(text = snack.emoji, fontSize = 13.sp)
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = snack.name,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = TextPrimary
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "${snack.protein}g P",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = Indigo400
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "+",
        fontSize = 12.sp,
        fontWeight = FontWeight.Black,
        color = Emerald400
      )
    }
  }
}

@Composable
fun IntakeLogRow(
  item: IntakeItem,
  onDelete: () -> Unit
) {
  val timeStr = remember(item.timestamp) {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    sdf.format(Date(item.timestamp))
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color(0xFF030712), RoundedCornerShape(14.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(14.dp))
      .padding(horizontal = 12.dp, vertical = 10.dp)
      .testTag("row_intake_${item.id}")
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
        Box(
          modifier = Modifier
            .size(34.dp)
            .background(Color(0x2210B981), RoundedCornerShape(8.dp))
            .border(0.8.dp, Color(0x4410B981), RoundedCornerShape(8.dp)),
          contentAlignment = Alignment.Center
        ) {
          Text(text = item.emoji, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Text(
            text = item.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Text(
            text = "${item.mealTiming} • $timeStr • ${item.calories} kcal",
            fontSize = 10.sp,
            color = TextSecondary
          )
        }
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .background(Color(0x226366F1), RoundedCornerShape(6.dp))
            .border(0.8.dp, Color(0x556366F1), RoundedCornerShape(6.dp))
            .padding(horizontal = 7.dp, vertical = 3.dp)
        ) {
          Text(
            text = "+${item.protein}g Protein",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = Indigo400
          )
        }

        Spacer(modifier = Modifier.width(4.dp))

        IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
          Text(text = "✕", color = Color(0xFF64748B), fontSize = 14.sp)
        }
      }
    }
  }
}
