package org.example.project

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.SourceHanSansCN_Regular
import org.jetbrains.compose.resources.Font

@Composable
fun App() {
    MaterialTheme(
        typography = Typography(
            defaultFontFamily = FontFamily(
                Font(Res.font.SourceHanSansCN_Regular)
            )
        )
    ) {
        Text("你好！Hello!")
    }
}

@Composable
fun AppFetchFont() {
    val fontFamilyState = fontFamilyStateOf()
    val fontFamily by remember { fontFamilyState }
    MaterialTheme(
        typography = Typography(
            defaultFontFamily = fontFamily ?: FontFamily.Default
        )
    ) {
        AnimatedContent(targetState = fontFamily) {
            if (it == null) {
                CircularProgressIndicator()
            } else {
                Text("你好！Hello!")
            }
        }
    }
}


