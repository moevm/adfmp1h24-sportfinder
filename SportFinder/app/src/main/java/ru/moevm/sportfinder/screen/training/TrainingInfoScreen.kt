package ru.moevm.sportfinder.screen.training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.persistentListOf
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrainingInfoScreen(state: TrainingInfoState) {
    val verticalScroll = rememberScrollState()

    val (name, tags, description) = state

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(verticalScroll)
            .padding(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "Название тренировки:", fontSize = 18.sp)
            val demoName = name.ifBlank { "(Пусто)" }
            Text(text = demoName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "Теги:", fontSize = 18.sp)
            if (tags.isEmpty()) {
                Text(text = "(Пусто)", fontSize = 18.sp)
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(tags) { tag ->
                        Chip(
                            colors = ChipDefaults.chipColors(
                                backgroundColor = SportFinderLightColorScheme.primary,
                                leadingIconContentColor = SportFinderLightColorScheme.onPrimary
                            ),
                            onClick = {}
                        ) {
                            Text(tag, color = SportFinderLightColorScheme.onPrimary)
                        }
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "Шаги тренировки:", fontSize = 18.sp)
            val demoDescription = description.ifBlank { "(Пусто)" }
            Text(text = demoDescription, fontSize = 18.sp)
        }
    }
}

@Preview
@Composable
private fun PreviewTrainingCreateScreen() {
    TrainingInfoScreen(
        state = TrainingInfoState(
            name = "Скакалка",
            tags = persistentListOf("Тренировка", "Асфальт", "Деревья"),
            description = "1 шаг: Провести разминку - вращение головой, вращение руками, вращение туловищем\n" +
                    "2 шаг: Взять скакалку, выполнить 80 прыжков, сделать 3 подхода с уменьшением количества прыжков каждый раз на 10\n"
        )
    )
}
