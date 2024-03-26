package ru.moevm.sportfinder.screen.training

data class TrainingCreateState(
    val name: String = "",
    val isEditNameMode: Boolean = false,
    val tags: List<String> = emptyList(),
    val description: String = "",
    val isEditDescriptionMode: Boolean = false,
    val isSelectTagDialogShown: Boolean = false
)
