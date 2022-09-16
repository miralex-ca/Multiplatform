package eu.baroncelli.dkmpsample.composables.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Screen
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetailParams
import eu.baroncelli.dkmpsample.composables.screens.countrieslist.ArticleDetailScreen
import eu.baroncelli.dkmpsample.composables.screens.countrieslist.ArticlesListScreen
import eu.baroncelli.dkmpsample.composables.screens.countrieslist.FavoriteListScreen


@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
) {

    when (screenIdentifier.screen) {

        Screen.FavoriteArticlesList ->
            FavoriteListScreen(
                countriesListState = stateProvider.get(screenIdentifier),
                onListItemClick = { navigate(Screen.ArticleDetail, ArticleDetailParams("Lorem ipsum dolor"))  }
            )

        Screen.ArticleDetail ->
            ArticleDetailScreen(
                countriesListState = stateProvider.get(screenIdentifier),
            )

        else -> ArticlesListScreen(
            countriesListState = stateProvider.get(screenIdentifier),
            onListItemClick = { navigate(Screen.ArticleDetail, ArticleDetailParams("Lorem ipsum dolor")) },
        )


//        CountriesList ->
//            CountriesListScreen(
//                countriesListState = stateProvider.get(screenIdentifier),
//                onListItemClick = { navigate(CountryDetail, CountryDetailParams(countryName = it)) },
//                onFavoriteIconClick = { events.selectFavorite(countryName = it) },
//            )
//
//        CountryDetail ->
//            CountryDetailScreen(
//                countryDetailState = stateProvider.get(screenIdentifier)
//            )

    }

}


@Composable
fun Navigation.TwoPaneDefaultDetail(
    screenIdentifier: ScreenIdentifier,
) {

    when (screenIdentifier.screen) {

//        CountriesList ->
//            CountriesListTwoPaneDefaultDetail()

        else -> Box {}
    }

}