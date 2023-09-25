package com.ianlor.wowdelivery.feature_delivery

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ianlor.wowdelivery.MainActivity
import com.ianlor.wowdelivery.TestTags
import com.ianlor.wowdelivery.di.AppModule
import com.ianlor.wowdelivery.feature_delivery.presentation.ScreenRoute
import com.ianlor.wowdelivery.feature_delivery.presentation.deliveries.DeliveriesScreen
import com.ianlor.wowdelivery.feature_delivery.presentation.delivery_detail.DeliveryDetailScreen
import com.ianlor.wowdelivery.ui.theme.WowDeliveryTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class DeliveriesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            WowDeliveryTheme() {
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.DeliveriesScreen.route
                ) {
                    composable(route = ScreenRoute.DeliveriesScreen.route) {
                        DeliveriesScreen(navController = navController)
                    }
                    composable(route = ScreenRoute.DeliveryDetailsScreen.route + "?id={id}",
                        arguments = listOf(
                            navArgument(name = "id") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                        DeliveryDetailScreen(navController = navController)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickDeliveryItem() {
        composeRule.onNodeWithText("Wow Delivery").assertIsDisplayed()
        composeRule.waitUntilDoesNotExist(hasTestTag(TestTags.DELIVERIES_LOADING))
        composeRule.onNodeWithTag(TestTags.DELIVERIES_LIST)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.DELIVERIES_LIST)
            .onChildren().onFirst().performClick()
        composeRule.onNodeWithTag(TestTags.DELIVERY_DETAIL_TOPBAR)
            .assertIsDisplayed()
        composeRule.waitUntilExactlyOneExists(hasTestTag(TestTags.DELIVERY_DETAIL_BODY))
        composeRule.onNodeWithTag(TestTags.DELIVERY_DETAIL_BODY)
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickDeliveryItemThenFavourite() {
        composeRule.onNodeWithText("Wow Delivery").assertIsDisplayed()
        composeRule.waitUntilDoesNotExist(hasTestTag(TestTags.DELIVERIES_LOADING))
        composeRule.onNodeWithTag(TestTags.DELIVERIES_LIST)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.DELIVERIES_LIST)
            .onChildren().onFirst().performClick()
        composeRule.onNodeWithTag(TestTags.DELIVERY_DETAIL_TOPBAR)
            .assertIsDisplayed()
        composeRule.waitUntilExactlyOneExists(hasTestTag(TestTags.DELIVERY_DETAIL_BODY))
        composeRule.onNodeWithTag(TestTags.DELIVERY_DETAIL_BODY)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add Favourite")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add Favourite")
            .performClick()

        composeRule.onNodeWithContentDescription("Remove Favourite")
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Back")
            .performClick()
        composeRule.onNodeWithTag(TestTags.DELIVERIES_LIST)
            .onChildren().onFirst()
            .assertContentDescriptionContains("Favourite")

    }
}