package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = IslamicDarkPrimary,
    onPrimary = Color(0xFF032617),
    primaryContainer = IslamicGreenPrimary,
    onPrimaryContainer = Color(0xFFBAF2D5),
    secondary = IslamicDarkGold,
    onSecondary = Color(0xFF382600),
    secondaryContainer = IslamicGoldDark,
    onSecondaryContainer = Color(0xFFFFE08A),
    tertiary = IslamicSage,
    background = IslamicDarkBg,
    onBackground = Color(0xFFE3EAE5),
    surface = IslamicDarkSurface,
    onSurface = Color(0xFFE3EAE5),
    surfaceVariant = IslamicDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFBAC7C0),
    outline = Color(0xFF334A3F)
)

private val LightColorScheme = lightColorScheme(
    primary = IslamicGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = IslamicMintContainer,
    onPrimaryContainer = IslamicGreenDark,
    secondary = IslamicGoldPrimary,
    onSecondary = Color.White,
    secondaryContainer = IslamicGoldContainer,
    onSecondaryContainer = IslamicGoldDark,
    tertiary = IslamicGreenLight,
    onTertiary = Color.White,
    background = IslamicCreamBg,
    onBackground = IslamicTextPrimary,
    surface = IslamicSurfaceWhite,
    onSurface = IslamicTextPrimary,
    surfaceVariant = IslamicSurfaceCream,
    onSurfaceVariant = IslamicTextSecondary,
    outline = IslamicCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
