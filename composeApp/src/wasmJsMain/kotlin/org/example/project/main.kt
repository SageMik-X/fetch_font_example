package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        Column {
            Box(modifier = Modifier.weight(1f)) {
                App()
            }
            Box(modifier = Modifier.weight(1f)) {
                AppFetchFont()
            }
        }
    }
}