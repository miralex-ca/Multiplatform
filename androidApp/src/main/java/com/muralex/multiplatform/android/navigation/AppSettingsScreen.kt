package com.muralex.multiplatform.android.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.viewmodel.screens.appsettings.AppSettingsState

@Composable
fun AppSettingsScreen(
    pageState: AppSettingsState,
    exitScreen: () -> Unit,
) {
    AppSettingsContent(
        exitScreen = exitScreen,
    )
}

@Composable
fun AppSettingsContent(
    exitScreen: () -> Unit,
) {

    Column {
        TopBarSettings(
            title = stringResource(id = R.string.settings),
            onBackClick = exitScreen
        )

        val state = remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut()
        ) {
            AppSettingsBox()
        }
    }
}

@Composable
private fun AppSettingsBox(

) {
    val openDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter) {

        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 300.dp)
                    .padding(12.dp)
            ) {

                Text(
                    text = "Interface",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 18.sp
                )

                DarkModeSetting(openDialog)

                Spacer(Modifier.height(5.dp))

                HomeSetting(openDialog)

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "News updates",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 18.sp
                )

                CountrySelection(openDialog)

                Spacer(Modifier.height(5.dp))

                RefreshData(openDialog)

                Spacer(Modifier.height(35.dp))

                AlertDialogSample(openDialog = openDialog)
            }
        }
    }
}



@Composable
private fun DarkModeSetting(openDialog: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .clickable { openDialog.value = true }
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = "Dark mode",
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )

        Text(
            text = "System default",
            modifier = Modifier
                .padding(2.dp)
                .alpha(0.8F),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp
        )
    }
}


@Composable
private fun HomeSetting(openDialog: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .clickable { openDialog.value = true }
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = "Click from the main list",
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )

        Text(
            text = "Display news in a bottom sheet",
            modifier = Modifier
                .padding(2.dp)
                .alpha(0.8F),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun CountrySelection(openDialog: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .clickable { openDialog.value = true }
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = "Select country",
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )

        Text(
            text = "Current: Canada",
            modifier = Modifier
                .padding(2.dp)
                .alpha(0.8F),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun RefreshData(openDialog: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .clickable { openDialog.value = true }
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = "App data updates",
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSettings(
    title: String = "",
    onBackClick: () -> Unit,
) {

    TopAppBar(
        title = { Text(text = title, fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}


@Composable
fun AlertDialogSample(
    openDialog: MutableState<Boolean>,
) {
    if (openDialog.value) {
        NoPaddingAlertDialog(
            titleText = "Select country",

            content = {
                RadioGroupSample(
                    radioOptions = listOf(
                        "Dark mode", "Light mode", "System default"
                    ),
                    onSelect = { openDialog.value = false },
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
    onSelect: () -> Unit = {},
) {

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onSelect()
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

