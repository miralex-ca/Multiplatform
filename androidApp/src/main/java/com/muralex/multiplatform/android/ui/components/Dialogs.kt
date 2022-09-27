package com.muralex.multiplatform.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun OptionDialog(
    radioOptions:  List<String> = emptyList(),
    openDialog: MutableState<Boolean>,
    optionSelectedIndex: (Int) -> Unit,
    selectedIndex: Int = 0,
) {

    if (openDialog.value) {
        NoPaddingAlertDialog(
            titleText = "Select country",
            content = {

                RadioGroupSample(
                    radioOptions = radioOptions,
                    onOptionSelect = {
                        optionSelectedIndex.invoke(it)
                        openDialog.value = false
                    },

                    selectedIndex = selectedIndex,
                )
            },

            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = { openDialog.value = false }
                ) {
                    Text(
                        text = "CANCEL",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        )
    }
}

@Composable
fun RadioGroupSample(
    radioOptions: List<String> = emptyList(),
    onOptionSelect: (Int) -> Unit = {},
    selectedIndex: Int = 0,
) {
    val selectedOptionIndex = if (selectedIndex > radioOptions.lastIndex) 0 else selectedIndex

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[selectedOptionIndex]) }

    Column(Modifier.selectableGroup()) {
        radioOptions.forEachIndexed  {index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onOptionSelect(index)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}



@Composable
fun NoPaddingAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    titleText: String = "",
    title: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (titleText.isEmpty()) {
                    title?.let {
                        CompositionLocalProvider {
                            val textStyle = MaterialTheme.typography.titleLarge
                            ProvideTextStyle(textStyle, it)
                        }
                    }
                } else {
                    Text(
                        text = titleText,
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 24.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 20.sp
                    )
                }
                content?.invoke()

                Box(Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 14.dp),
                    ) {
                        dismissButton?.invoke()
                        confirmButton()
                    }
                }
            }
        }
    }
}
