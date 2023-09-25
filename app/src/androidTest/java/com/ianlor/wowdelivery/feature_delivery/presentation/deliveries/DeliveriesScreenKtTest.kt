package com.ianlor.wowdelivery.feature_delivery.presentation.deliveries

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ianlor.wowdelivery.MainActivity
import com.ianlor.wowdelivery.TestTags
import com.ianlor.wowdelivery.di.AppModule
import com.ianlor.wowdelivery.feature_delivery.presentation.ScreenRoute
import com.ianlor.wowdelivery.ui.theme.WowDeliveryTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class DeliveriesScreenKtTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()

        composeRule.activity.setContent {
            val navController = rememberNavController()
            WowDeliveryTheme() {
                NavHost(navController = navController, startDestination = ScreenRoute.DeliveriesScreen.route){
                    composable(route = ScreenRoute.DeliveriesScreen.route){
                        DeliveriesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun listOfDeliveries_isVisible(){
        composeRule.onNodeWithText("Wow Delivery").assertIsDisplayed()
        composeRule.waitUntilDoesNotExist(hasTestTag(TestTags.DELIVERIES_LOADING))
        composeRule.onNodeWithTag(TestTags.DELIVERIES_LIST).assertIsDisplayed()
    }
}