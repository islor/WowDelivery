package com.ianlor.wowdelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ianlor.wowdelivery.feature_delivery.presentation.ScreenRoute
import com.ianlor.wowdelivery.feature_delivery.presentation.deliveries.DeliveriesScreen
import com.ianlor.wowdelivery.feature_delivery.presentation.delivery_detail.DeliveryDetailScreen
import com.ianlor.wowdelivery.ui.theme.WowDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WowDeliveryTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

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
    }
}