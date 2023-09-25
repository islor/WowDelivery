package com.ianlor.wowdelivery.feature_delivery.presentation.deliveries

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import com.ianlor.wowdelivery.feature_delivery.presentation.components.RowLabelText
import com.ianlor.wowdelivery.feature_delivery.util.NumberFormatUtil
import com.ianlor.wowdelivery.ui.theme.WowDeliveryTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DeliveryItem(
    delivery: Delivery,
    isFavourite: Boolean,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.padding(horizontal = 16.dp),
        elevation = 5.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            if (isFavourite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .size(32.dp),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 28.dp, vertical = 24.dp)
            ) {
                AsyncImage(
                    model = delivery.goodsPicture,
                    contentDescription = delivery.remarks,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(modifier = Modifier.weight(1f)) {

                            RowLabelText(
                                label = "From:",
                                value = "${delivery.route.start}",
                                style = MaterialTheme.typography.body2,
                            )
                            RowLabelText(
                                label = "To:",
                                value = "${delivery.route.end}",
                                style = MaterialTheme.typography.body2,
                            )
                            RowLabelText(
                                    label = "$:",
                            value = NumberFormatUtil.sumOfCurrency(delivery.deliveryFee, delivery.surcharge),
                            style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = delivery.remarks,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun DeliveryItemPreview() {

    WowDeliveryTheme {
        DeliveryItem(
            isFavourite = true,
            delivery = Delivery(
                id = 1,
                remoteId = "abc",
                goodsPicture = "Delivery A",
                remarks = "This is a remark This is a remark This is a remark This is a remark This is a remark This is a remark This is a remark",
                pickupTime = "2014-10-06T10:45:38-08:00",
                deliveryFee = "$92",
                surcharge = "$122",
                route = Delivery.Route(
                    start = "Central, Hong Lkong, Hong Kong",
                    end = "TST"
                ),
                sender = Delivery.Sender(
                    phone = "2334144",
                    name = "Ken",
                    "kenisenough@mail.com"
                ),
            )
        )
    }
}