package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = Emerald400,
  onPrimary = Color(0xFF022C22),
  primaryContainer = Color(0xFF064E3B),
  onPrimaryContainer = Color(0xFFA7F3D0),

  secondary = Indigo400,
  onSecondary = Color(0xFF1E1B4B),
  secondaryContainer = Color(0xFF312E81),
  onSecondaryContainer = Color(0xFFC7D2FE),

  tertiary = Amber400,
  onTertiary = Color(0xFF451A03),
  tertiaryContainer = Color(0xFF78350F),
  onTertiaryContainer = Color(0xFFFDE68A),

  background = GymDarkBackground,
  onBackground = TextPrimary,
  surface = GymDarkSurface,
  onSurface = TextPrimary,
  surfaceVariant = GymDarkSurfaceVariant,
  onSurfaceVariant = TextSecondary,
  outline = GymBorder,
  error = Rose500,
  onError = Color.White
)

@Composable
fun GymDostTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}
