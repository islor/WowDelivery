package com.ianlor.wowdelivery.feature_delivery.presentation.deliveries

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import com.ianlor.wowdelivery.feature_delivery.presentation.ScreenRoute

@OptIn(ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun DeliveriesScreen(
    viewModel: DeliveriesViewModel = hiltViewModel(),
    navController: NavController
) {
    val deliveries =
        viewModel.deliveryPagingFlow.collectAsLazyPagingItems()
    val refreshing = deliveries.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(
        deliveries.loadState.refresh is LoadState.Loading,
        { deliveries.refresh() })

//    val context = LocalContext.current
//    LaunchedEffect(key1 = deliveries.loadState, block = {
//
//        if (deliveries.loadState.refresh is LoadState.Error) {
//            Toast.makeText(
//                context,
//                "Error: " + (deliveries.loadState.refresh as LoadState.Error).error.message,
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    })

    Scaffold(topBar = {

        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Wow Delivery",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding()
        ) {
            PullRefreshIndicator(
                refreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
            if (deliveries.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        count = deliveries.itemCount,
                        key = deliveries.itemKey { it.id },
                        contentType = deliveries.itemContentType { "DeliveryItems" }) { index ->

                        val deliveryItem = deliveries[index]
                        if (deliveryItem != null) {
                            if (deliveryItem.route.start.isNotEmpty() && deliveryItem.route.end.isNotEmpty()) {
                                DeliveryItem(
                                    delivery = deliveryItem,
                                    isFavourite = viewModel.favouriteListState.contains(
                                        deliveryItem.remoteId
                                    ),
                                    modifier = Modifier.clickable {
                                        navController.navigate(
                                            ScreenRoute.DeliveryDetailsScreen.route + "?id=${deliveryItem.id}"
                                        )
                                    })
                            }
                        }
                    }
                    item {
                        if (deliveries.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }


}