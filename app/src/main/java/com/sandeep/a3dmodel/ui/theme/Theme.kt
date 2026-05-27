package com.sandeep.a3dmodel.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AccentGold,
    secondary = AccentSky,
    tertiary = AccentMint,
    background = DeepInk,
    surface = DeepSlate,
    onPrimary = DeepInk,
    onSecondary = DeepInk,
    onTertiary = DeepInk,
    onBackground = OnSurfaceBright,
    onSurface = OnSurfaceBright
)

private val LightColorScheme = lightColorScheme(
    primary = AccentGold,
    secondary = AccentSky,
    tertiary = AccentCoral,
    background = DeepInk,
    surface = SurfaceNight,
    onPrimary = DeepInk,
    onSecondary = DeepInk,
    onTertiary = DeepInk,
    onBackground = OnSurfaceBright,
    onSurface = OnSurfaceBright
)

@Composable
fun A3DModelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
