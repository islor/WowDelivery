package com.ianlor.wowdelivery.feature_delivery.presentation.delivery_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ianlor.wowdelivery.TestTags
import com.ianlor.wowdelivery.feature_delivery.presentation.components.RowLabelText
import com.ianlor.wowdelivery.feature_delivery.util.NumberFormatUtil


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryDetailScreen(
    navController: NavController,
    viewModel: DeliveryDetailViewModel = hiltViewModel()
) {
    val delivery = viewModel.delivery.value

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Delivery Details",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            modifier = Modifier.testTag(TestTags.DELIVERY_DETAIL_TOPBAR)
        )
    }) { padding ->

        if (delivery == null)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .testTag(TestTags.DELIVERY_DETAIL_EMPTY)
            ) {
                Text(
                    text = "Delivery not found.",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .testTag(TestTags.DELIVERY_DETAIL_BODY)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(color = MaterialTheme.colors.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            RowLabelText(
                                label = "From:",
                                value = "${delivery.route.start}",
                                style = MaterialTheme.typography.body1,
                            )
                            RowLabelText(
                                label = "To:",
                                value = "${delivery.route.end}",
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(color = MaterialTheme.colors.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            AsyncImage(
                                model = delivery.goodsPicture,
                                contentDescription = delivery.remarks,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = delivery.remarks,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(color = MaterialTheme.colors.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            verticalArrangement = Arrangement.Center
                        ) {
                            RowLabelText(
                                label = "Delivery Fee:",
                                value = NumberFormatUtil.sumOfCurrency(
                                    delivery.deliveryFee,
                                    ""
                                ),
                                style = MaterialTheme.typography.body1,
                            )
                            RowLabelText(
                                label = "Surcharge:",
                                value = NumberFormatUtil.sumOfCurrency(
                                    delivery.surcharge,
                                    ""
                                ),
                                style = MaterialTheme.typography.body1,
                            )
                            RowLabelText(
                                label = "Total:",
                                value = NumberFormatUtil.sumOfCurrency(
                                    delivery.deliveryFee,
                                    delivery.surcharge
                                ),
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(56.dp))

                }

                FloatingActionButton(
                    onClick = {
                        if (viewModel.isFavourite.value) {
                            viewModel.onEvent(
                                DeliveryDetailEvents.OnRemoveFavourite(
                                    delivery.remoteId
                                )
                            )
                        } else {
                            viewModel.onEvent(
                                DeliveryDetailEvents.OnAddFavourite(
                                    delivery.remoteId
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                ) {
                    if (viewModel.isFavourite.value) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Remove Favourite"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Add Favourite"
                        )
                    }
                }
            }

        }
    }
}

//
//@Preview
//@Composable
//fun DeliveryItemPreview() {
//
//    WowDeliveryTheme {
//        DeliveryDetailScreen(
//            navController = rememberNavController(),
//            delivery = Delivery(
//                id = 1,
//                remoteId = "abc",
//                goodsPicture = "Delivery A",
//                remarks = "This is a remark This is a remark This is a remark This is a remark This is a remark This is a remark This is a remark",
//                pickupTime = "2014-10-06T10:45:38-08:00",
//                deliveryFee = "$92",
//                surcharge = "$1221242",
//                route = Delivery.Route(
//                    start = "Central, Hong Lkong, Hong Kong",
//                    end = "TST"
//                ),
//                sender = Delivery.Sender(
//                    phone = "2334144",
//                    name = "Ken",
//                    "kenisenough@mail.com"
//                ),
//            )
//        )
//    }
//}