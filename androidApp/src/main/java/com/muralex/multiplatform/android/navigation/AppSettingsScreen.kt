package com.muralex.multiplatform.android.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.android.ui.components.OptionDialog
import com.muralex.multiplatform.viewmodel.screens.appsettings.AppSettingsState

@Composable
fun AppSettingsScreen(
    pageState: AppSettingsState,
    selectCountry: (Int) -> Unit,
    selectThemeMode: (Int) -> Unit,
    selectOpenFromList: (Int) -> Unit,
    selectApiSource: (Int) -> Unit,
    exitScreen: () -> Unit,
) {
    AppSettingsContent(
        pageState = pageState,
        selectCountry = selectCountry,
        selectThemeMode = selectThemeMode,
        selectOpenFromList = selectOpenFromList,
        selectApiSource = selectApiSource,
        exitScreen = exitScreen,
    )
}


@Composable
fun AppSettingsContent(
    pageState: AppSettingsState,
    selectCountry: (Int) -> Unit,
    selectThemeMode: (Int) -> Unit,
    selectOpenFromList: (Int) -> Unit,
    selectApiSource: (Int) -> Unit,
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
            AppSettingsBox(
                pageState = pageState,
                selectCountry = selectCountry,
                selectThemeMode = selectThemeMode,
                selectOpenFromList = selectOpenFromList,
                selectApiSource = selectApiSource,
            )
        }
    }
}

@Composable
private fun AppSettingsBox(
    pageState: AppSettingsState,
    selectCountry: (Int) -> Unit,
    selectThemeMode: (Int) -> Unit,
    selectOpenFromList: (Int) -> Unit,
    selectApiSource: (Int) -> Unit,
) {

    val modesList =  stringArrayResource(R.array.themes_options_text).toList()
    val openFromLIst = stringArrayResource(R.array.open_from_list_options_text).toList()
    val countriesList = stringArrayResource(R.array.countries_options_text).toList()
    val dataSource = stringArrayResource(R.array.apisource_options_text).toList()

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

                // TODO add texts to resources
                Text(
                    text = "Interface",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 18.sp
                )

                OptionsWithDialog(
                    radioOptions = modesList,
                    title = "Select mode",
                    summary = modesList[pageState.savedThemeMode],
                    optionSelectedIndex = {selectThemeMode(it)},
                    selectedIndex = pageState.savedThemeMode,
                )

//                Spacer(Modifier.height(5.dp))
//
//                OptionsWithDialog(
//                    radioOptions = openFromLIst,
//                    title = "Select open option",
//                    summary = openFromLIst[pageState.openFromList],
//                    optionSelectedIndex = { selectOpenFromList(it) },
//                    selectedIndex = pageState.openFromList,
//                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "News updates",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 18.sp
                )

                OptionsWithDialog(
                    radioOptions = countriesList,
                    title = "Select country",
                    summary = countriesList[pageState.sourceCountry],
                    optionSelectedIndex = {selectCountry(it)},
                    selectedIndex = pageState.sourceCountry,
                )

                Spacer(Modifier.height(5.dp))

                OptionsWithDialog(
                    radioOptions = dataSource,
                    title = "Data source",
                    summary = dataSource[pageState.apiDataSource],
                    optionSelectedIndex = {selectApiSource(it)},
                    selectedIndex = pageState.apiDataSource,
                )

                Spacer(Modifier.height(35.dp))
            }
        }
    }
}


@Composable
private fun OptionsWithDialog(
    radioOptions: List<String> = emptyList(),
    title: String = "",
    summary: String = "",
    optionSelectedIndex: (Int) -> Unit,
    selectedIndex: Int = 0,
) {

    val openDialog = remember { mutableStateOf(false) }

    OptionsSetting(
        openDialog = openDialog,
        title = title,
        summary = summary,
    )

    OptionDialog(
        openDialog = openDialog,
        selectedIndex = selectedIndex,
        optionSelectedIndex = optionSelectedIndex,
        radioOptions = radioOptions,
    )
}

@Composable
private fun OptionsSetting(
    openDialog: MutableState<Boolean>,
    title: String = "",
    summary: String = "",
) {
    Column(
        modifier = Modifier
            .clickable { openDialog.value = true }
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = title,
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )

        Text(
            text = "Current: $summary",
            modifier = Modifier
                .padding(2.dp)
                .alpha(0.8F),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp
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



