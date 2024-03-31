package ru.moevm.sportfinder.screen.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.collections.immutable.ImmutableList
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun SelectListAlertDialog(
    listItems: ImmutableList<String>,
    initialCheckedIndex: ImmutableList<Int>,
    onSaveClick: (List<Int>) -> Unit,
    onDismiss: () -> Unit
) {
    val checkedList = remember(listItems) {
            mutableStateListOf<Boolean>().apply {
                repeat(listItems.size) { index ->
                    add(index in initialCheckedIndex)
                }
            }
        }

    Dialog(onDismissRequest = onDismiss) {
        LazyColumn(
            modifier = Modifier
                .background(SportFinderLightColorScheme.onPrimary)
                .padding(16.dp)
        ) {
            itemsIndexed(listItems) { i, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        checkedList[i] = !checkedList[i]
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedList[i],
                        colors = CheckboxDefaults.colors(checkedColor = SportFinderLightColorScheme.primary),
                        onCheckedChange = { checkedList[i] = it }
                    )
                    Text(item, fontSize = 22.sp)
                }
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    CommonButton(
                        onClick = {
                            val result = mutableListOf<Int>()
                            checkedList.forEachIndexed { index, state ->
                                if (state) {
                                    result.add(index)
                                }
                            }
                            onSaveClick(result)
                            onDismiss()
                        },
                        buttonText = "Сохранить"
                    )
                }
            }
        }
    }
}