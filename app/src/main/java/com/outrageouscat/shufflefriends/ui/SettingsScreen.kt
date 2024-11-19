package com.outrageouscat.shufflefriends.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.data.datastore.settingsDataStore
import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal
import com.outrageouscat.shufflefriends.ui.composables.rememberDatePickerDialog
import com.outrageouscat.shufflefriends.ui.dialogs.CustomMessageConfigDialog
import com.outrageouscat.shufflefriends.ui.util.toMonthName
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    context: Context,
    modifier: Modifier,
    onBack: () -> Unit
) {

    val configDataStore = context.settingsDataStore
    val settings by configDataStore.data.collectAsState(initial = SettingsLocal.getDefaultInstance())
    var deliveryDate by remember { mutableStateOf("") }

    var showCustomMessageDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(settings.deliveryDate) {
        deliveryDate = settings.deliveryDate
    }

    val datePickerDialog = rememberDatePickerDialog(onDateSelected = { day, month ->
        deliveryDate = "$day de ${month.toMonthName(context)}"
        scope.launch {
            configDataStore.updateData {
                it.toBuilder()
                    .setDeliveryDate(deliveryDate)
                    .build()
            }
        }
    })

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.shuffle_friends_gift_icon),
                        contentDescription = stringResource(R.string.content_description_app_logo),
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.FillBounds
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_back_icon)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
            ) {
                Text(
                    text = stringResource(R.string.settings_screen_title),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W300,
                )

                // Setting options
                // Custom Message
                Row(
                    modifier = Modifier
                        .clickable(
                            onClick = { showCustomMessageDialog = true }
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        text = "Mensaje de WhatsApp",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W300,
                    )

                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.eye_icon),
                            contentDescription = "Ver mensaje de WhatsApp"
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    thickness = 1.dp
                )

                // Delivery date
                Row(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                datePickerDialog.show()
                            }
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        text = "Fecha de entrega",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W300,
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterVertically),
                        text = deliveryDate,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300,
                        fontStyle = FontStyle.Italic
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    thickness = 1.dp
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                text = "© IamCleverito | Outragous Cat\nVersion 1.0 2024",
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                fontStyle = FontStyle.Italic
            )

            if (showCustomMessageDialog) {
                CustomMessageConfigDialog(
                    onConfirm = { customMessage ->
                        scope.launch {
                            configDataStore.updateData {
                                it.toBuilder()
                                    .setCustomMessage(customMessage)
                                    .build()
                            }
                        }
                    },
                    onDismiss = { showCustomMessageDialog = false }
                )
            }
        }
    }
}
