package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.GymDarkSurface
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.AppTab

@Composable
fun GymBottomBar(
  currentTab: AppTab,
  onTabSelected: (AppTab) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    modifier = modifier
      .background(GymDarkSurface)
      .border(0.8.dp, GymBorder)
      .windowInsetsPadding(WindowInsets.navigationBars),
    containerColor = GymDarkSurface,
    contentColor = TextPrimary
  ) {
    AppTab.values().forEach { tab ->
      val selected = currentTab == tab
      NavigationBarItem(
        selected = selected,
        onClick = { onTabSelected(tab) },
        icon = {
          Text(text = tab.icon, fontSize = if (selected) 20.sp else 16.sp)
        },
        label = {
          Text(
            text = when (tab) {
              AppTab.DASHBOARD -> "Dashboard"
              AppTab.INTAKE -> "Aaj Khaya"
              AppTab.WORKOUT -> "Workout"
              AppTab.DIET -> "Diet"
              AppTab.INFO -> "Info"
            },
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Emerald400 else TextSecondary
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = Emerald400,
          selectedTextColor = Emerald400,
          unselectedIconColor = TextSecondary,
          unselectedTextColor = TextSecondary,
          indicatorColor = Color(0x2210B981)
        ),
        modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
      )
    }
  }
}
