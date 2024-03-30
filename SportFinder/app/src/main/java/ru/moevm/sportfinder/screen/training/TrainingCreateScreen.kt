package ru.moevm.sportfinder.screen.training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.moevm.sportfinder.screen.common_components.CommonButton
import ru.moevm.sportfinder.screen.common_components.CommonTextField
import ru.moevm.sportfinder.screen.common_components.SelectListAlertDialog
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrainingCreateScreen(
    state: TrainingCreateState,
    availableTags: ImmutableList<String>,
    onChangeNameClick: () -> Unit,
    onChangeName: (String) -> Unit,
    onShowSelectTagsDialogClick: () -> Unit,
    onSaveSelectedTagsClick: (List<Int>) -> Unit,
    onDismissSelectTagsDialogClick: () -> Unit,
    onRemoveTagClick: (Int) -> Unit,
    onChangeDescriptionClick: () -> Unit,
    onChangeDescription: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    val verticalScroll = rememberScrollState()

    val (name, isEditNameMode, tags, description, isEditDescriptionMode, isSelectTagDialogShown) = state

    if (isSelectTagDialogShown) {
        SelectListAlertDialog(
            listItems = availableTags,
            initialCheckedIndex = persistentListOf(),
            onSaveClick = onSaveSelectedTagsClick,
            onDismiss = onDismissSelectTagsDialogClick
        )
    }

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
            if (isEditNameMode) {
                CommonTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = name,
                    hint = "Введите название",
                    onTextChanged = onChangeName,
                )
            } else {
                val demoName = name.ifBlank { "(Пусто)" }
                Text(text = demoName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            CommonButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onChangeNameClick,
                buttonText = if (isEditNameMode) {
                    "Сохранить"
                } else {
                    "Изменить название"
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "Теги:", fontSize = 18.sp)
            if (tags.isEmpty()) {
                Text(text = "(Пусто)", fontSize = 18.sp)
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    itemsIndexed(tags) { index, tag ->
                        Chip(
                            colors = ChipDefaults.chipColors(
                                backgroundColor = SportFinderLightColorScheme.primary,
                                leadingIconContentColor = SportFinderLightColorScheme.onPrimary
                            ),
                            onClick = {},
                            leadingIcon = { Icon(Icons.Outlined.Clear, null, modifier = Modifier.clickable { onRemoveTagClick(index) }) }
                        ) {
                            Text(tag, color = SportFinderLightColorScheme.onPrimary)
                        }
                    }
                }
            }

            CommonButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onShowSelectTagsDialogClick,
                buttonText = "Добавить теги"
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "Шаги тренировки:", fontSize = 18.sp)
            if (isEditDescriptionMode) {
                CommonTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    hint = "Введите шаги тренировки",
                    isSingleLine = false,
                    onTextChanged = onChangeDescription,
                )
            } else {
                val demoDescription = description.ifBlank { "(Пусто)" }
                Text(text = demoDescription, fontSize = 18.sp)
            }
            CommonButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onChangeDescriptionClick,
                buttonText = if (isEditDescriptionMode) {
                    "Сохранить"
                } else {
                    "Изменить шаги"
                }
            )
        }

        CommonButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveClick,
            buttonText = "Сохранить"
        )
    }
}

@Preview
@Composable
private fun PreviewTrainingCreateScreen() {
    TrainingCreateScreen(
        state = TrainingCreateState(
            name = "MyTraining",
            isEditNameMode = false,
            tags = listOf("Бег", "Тренировка"),
            "1. MyDescription\n" +
                    "2. MySecondDescription",
            isEditDescriptionMode = false,
            isSelectTagDialogShown = false
        ),
        persistentListOf("Тренировка", "Бег", "Асфальт", "Деревья", "Стадион"),
        {}, {}, {}, {}, {}, {}, {}, {}, {}
    )
}
