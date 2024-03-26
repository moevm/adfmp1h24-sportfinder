package ru.moevm.sportfinder.screen.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "Разработчики:")
            Text(text = "Асташёнок Михаил (mikeasta)")
            Text(text = "Нагибин Игорь (NaGIBIN-IGOR)")
            Text(text = "Шквиря Евгений (Jengle88)")
        }
    }

}