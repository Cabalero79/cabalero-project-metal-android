package com.cabalero.projectmetal.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
  primary = AccentSteel,
  onPrimary = BackgroundDark,
  background = BackgroundDark,
  onBackground = TextPrimary,
  surface = SurfaceDark,
  onSurface = TextPrimary,
  surfaceVariant = CardDark,
  onSurfaceVariant = TextSecondary,
)

@Composable
fun CabaleroTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = AppColorScheme,
    typography = AppTypography,
    content = content,
  )
}

