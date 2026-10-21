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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.GymDostData
import com.example.model.DietMealPlan
import com.example.model.QuickFoodItem
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.GymDarkSurface
import com.example.ui.theme.GymDarkSurfaceVariant
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Sky400
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DietScreen(
  onLogMeal: (name: String, calories: Int, protein: Int, emoji: String) -> Unit,
  onOpenLogCustomFood: (prefillName: String, cals: Int, prot: Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var searchQuery by remember { mutableStateOf("") }

  val filteredLibrary = remember(searchQuery) {
    if (searchQuery.isBlank()) {
      GymDostData.FOOD_LIBRARY
    } else {
      GymDostData.FOOD_LIBRARY.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
          it.description.contains(searchQuery, ignoreCase = true)
      }
    }
  }

  fun searchGoogleFacts(food: String) {
    if (food.isBlank()) return
    val query = URLEncoder.encode("$food food nutrition facts calories protein carbs fat per 100g", StandardCharsets.UTF_8.toString())
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=$query"))
    context.startActivity(intent)
  }

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
            Text(text = "🥑", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Recommended Diet Plan",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }
          Text(
            text = "Google Nutrition Search aur 1-Click Intake Add integration",
            fontSize = 11.sp,
            color = TextSecondary
          )
        }

        Box(
          modifier = Modifier
            .background(Color(0x2238BDF8), RoundedCornerShape(8.dp))
            .border(0.8.dp, Color(0x5538BDF8), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(text = "Macro Optimized", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Sky400)
        }
      }
    }

    // SMART FOOD NUTRITION SEARCH CARD
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
              text = "🔍 Smart Food Nutrition Search",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Sky400
            )

            Box(
              modifier = Modifier
                .background(Color(0xFF1E293B), RoundedCornerShape(6.dp))
                .border(0.8.dp, GymBorder, RoundedCornerShape(6.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(text = "Google Filtered", fontSize = 9.sp, color = TextSecondary)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Search text field
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search food (e.g. 100g Peanuts, Eggs, Whey)...", fontSize = 11.sp, color = Color.Gray) },
            singleLine = true,
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { searchQuery = "" }, modifier = Modifier.size(24.dp)) {
                  Text(text = "✕", fontSize = 12.sp, color = TextSecondary)
                }
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_diet_search"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary,
              focusedBorderColor = Sky400,
              unfocusedBorderColor = GymBorder,
              focusedContainerColor = Color(0xFF030712),
              unfocusedContainerColor = Color(0xFF030712)
            ),
            shape = RoundedCornerShape(12.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Action buttons: Google Facts & + Log Khana
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { searchGoogleFacts(searchQuery.ifBlank { "Peanuts food nutrition" }) },
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0284C7),
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_google_facts")
            ) {
              Text(text = "Google Facts 🔍", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = {
                val match = GymDostData.FOOD_LIBRARY.firstOrNull {
                  it.name.contains(searchQuery, ignoreCase = true)
                }
                onOpenLogCustomFood(
                  searchQuery.ifBlank { "Logged Food" },
                  match?.calories ?: 0,
                  match?.protein ?: 0
                )
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = Emerald400,
                contentColor = Color(0xFF022C22)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_quick_log_search")
            ) {
              Text(text = "+ Log Khana 🍽️", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Filtered food chips
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 2.dp)
          ) {
            items(filteredLibrary) { item ->
              FoodLibraryChip(
                item = item,
                onLog = {
                  onLogMeal(item.name, item.calories, item.protein, item.emoji)
                }
              )
            }
          }
        }
      }
    }

    // Recommended Diet Schedule Title
    item {
      Text(
        text = "Recommended Daily Meal Schedule",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    }

    // Recommended Diet Plan List
    items(GymDostData.DEFAULT_DIET_PLAN, key = { it.id }) { meal ->
      DietPlanCard(
        meal = meal,
        onLogDirect = {
          onLogMeal(meal.name.substringAfter(": "), meal.calories, meal.protein, meal.emoji)
        },
        onSearchGoogle = {
          searchGoogleFacts(meal.name)
        }
      )
    }
  }
}

@Composable
fun FoodLibraryChip(
  item: QuickFoodItem,
  onLog: () -> Unit
) {
  Box(
    modifier = Modifier
      .background(Color(0xFF030712), RoundedCornerShape(10.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(10.dp))
      .clickable { onLog() }
      .padding(horizontal = 9.dp, vertical = 6.dp)
      .testTag("chip_food_${item.name}")
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(text = item.emoji, fontSize = 12.sp)
      Spacer(modifier = Modifier.width(5.dp))
      Text(
        text = item.name,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = TextPrimary
      )
      Spacer(modifier = Modifier.width(5.dp))
      Text(
        text = "${item.protein}g P",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = Sky400
      )
      Spacer(modifier = Modifier.width(3.dp))
      Text(
        text = "+ Log",
        fontSize = 10.sp,
        fontWeight = FontWeight.Black,
        color = Emerald400
      )
    }
  }
}

@Composable
fun DietPlanCard(
  meal: DietMealPlan,
  onLogDirect: () -> Unit,
  onSearchGoogle: () -> Unit
) {
  val context = LocalContext.current

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color(0xFF0F172A), RoundedCornerShape(14.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(14.dp))
      .padding(12.dp)
      .testTag("card_diet_${meal.id}")
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
        // 3D Icon via Coil with emoji fallback
        Box(
          modifier = Modifier
            .size(38.dp)
            .background(Color(0xFF1E293B), RoundedCornerShape(10.dp))
            .border(0.8.dp, Color(0xFF334155), RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center
        ) {
          if (meal.iconUrl.isNotEmpty()) {
            AsyncImage(
              model = ImageRequest.Builder(context)
                .data(meal.iconUrl)
                .crossfade(true)
                .build(),
              contentDescription = meal.name,
              modifier = Modifier.size(26.dp),
              contentScale = ContentScale.Fit
            )
          } else {
            Text(text = meal.emoji, fontSize = 18.sp)
          }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Text(
            text = meal.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            lineHeight = 16.sp
          )
          Text(
            text = meal.description,
            fontSize = 10.sp,
            color = TextSecondary,
            lineHeight = 14.sp
          )
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Column(horizontalAlignment = Alignment.End) {
          Box(
            modifier = Modifier
              .background(Color(0x2238BDF8), RoundedCornerShape(6.dp))
              .border(0.8.dp, Color(0x4438BDF8), RoundedCornerShape(6.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${meal.calories} kcal",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Sky400
            )
          }
          Text(
            text = "${meal.protein}g Protein",
            fontSize = 9.sp,
            color = TextSecondary,
            modifier = Modifier.padding(top = 2.dp)
          )
        }

        // Direct log button
        OutlinedButton(
          onClick = onLogDirect,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0x2210B981),
            contentColor = Emerald400
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x4410B981)),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
          modifier = Modifier.testTag("btn_log_meal_${meal.id}")
        ) {
          Text(text = "+ Log", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }

        // Google search button
        IconButton(
          onClick = onSearchGoogle,
          modifier = Modifier.size(26.dp)
        ) {
          Text(text = "🔍", fontSize = 11.sp)
        }
      }
    }
  }
}
