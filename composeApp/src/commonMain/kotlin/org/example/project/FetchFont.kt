package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight


@Composable
fun fontFamilyStateOf(): State<FontFamily?> {
    val fontState =
        fontStateOf("composeResources/kotlinproject.composeapp.generated.resources/font/SourceHanSansCN-Regular.otf")
    return derivedStateOf {
        return@derivedStateOf if (fontState.value == null) {
            null
        } else {
            FontFamily(fontState.value!!)
        }
    }
}

@Composable
fun fontStateOf(
    path: String,
    weight: FontWeight = FontWeight.Normal,
    style: FontStyle = FontStyle.Normal
): State<Font?> {
    val state = mutableStateOf<Font?>(null)
    LaunchedEffect(path, weight, style) {
        state.value = fetchFont(path, weight, style)
    }
    return state
}

expect suspend fun fetchFont(
    path: String,
    weight: FontWeight,
    style: FontStyle
): Font