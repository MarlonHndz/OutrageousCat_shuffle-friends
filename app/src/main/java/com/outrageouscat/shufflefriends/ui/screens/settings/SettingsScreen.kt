package com.outrageouscat.shufflefriends.ui.screens.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.outrageouscat.shufflefriends.R
import com.outrageouscat.shufflefriends.ui.composables.rememberDatePickerDialog
import com.outrageouscat.shufflefriends.ui.dialogs.CustomMessageConfigDialog
import com.outrageouscat.shufflefriends.ui.dialogs.PreviewWhatsappMessageDialog
import com.outrageouscat.shufflefriends.ui.navigation.Screen
import com.outrageouscat.shufflefriends.ui.util.toLocalizedOrdinal
import com.outrageouscat.shufflefriends.ui.util.toMonthName
import com.outrageouscat.shufflefriends.ui.util.toOrdinal
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    context: Context,
    modifier: Modifier,
    onBack: () -> Unit,
    navController: NavHostController,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val settings by viewModel.settings.collectAsState()

    var showEditCustomMessageDialog by remember { mutableStateOf(false) }
    var showCustomMessagePreviewDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val datePickerDialog = rememberDatePickerDialog(onDateSelected = { day, month ->
        val deliveryDate = context.getString(
            R.string.whatsapp_message_delivery_date_value,
            day.toLocalizedOrdinal(context),
            month.toMonthName(context)
        )
        viewModel.updateDeliveryDate(deliveryDate)
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
                            imageVector = Icons.Default.ArrowBack,
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
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.settings_screen_title),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W300,
                )

                // Setting options
                // Custom Message
                Row(
                    modifier = Modifier
                        .clickable(
                            onClick = { showEditCustomMessageDialog = true }
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.settings_option_whatsapp_message),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W300,
                    )

                    IconButton(
                        onClick = { showCustomMessagePreviewDialog = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.eye_icon),
                            contentDescription = stringResource(R.string.content_description_whatsapp_eye_icon)
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
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.settings_option_delivery_date),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W300,
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterVertically),
                        text = settings.deliveryDate,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300,
                        fontStyle = FontStyle.Italic
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    thickness = 1.dp
                )

                // About us section
                Text(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 40.dp,
                        bottom = 8.dp
                    ),
                    text = stringResource(R.string.settings_section_title_help_and_opinion),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W300,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.AboutUs.route)
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        text = stringResource(R.string.settings_option_about_us),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W300,
                    )

                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    thickness = 1.dp
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            openPlayStore(context.packageName, context)
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        text = stringResource(R.string.settings_option_rate_app),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W300,
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
                text = stringResource(R.string.developer_water_mark),
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                fontStyle = FontStyle.Italic
            )
            Image(
                modifier = Modifier
                    .height(65.dp)
                    .padding(8.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(R.drawable.outrageous_cat_logo_no_bg),
                contentDescription = stringResource(R.string.content_description_outrageous_cat_logo),
            )

            if (showEditCustomMessageDialog) {
                CustomMessageConfigDialog(
                    initialCustomMessage = settings.customMessage,
                    onConfirm = { newCustomMessage ->
                        scope.launch {
                            viewModel.updateCustomMessage(newCustomMessage)
                        }
                        showEditCustomMessageDialog = false
                    },
                    onDismiss = { showEditCustomMessageDialog = false }
                )
            }

            if (showCustomMessagePreviewDialog) {
                PreviewWhatsappMessageDialog(
                    context = context,
                    customMessage = settings.customMessage,
                    deliveryDate = settings.deliveryDate,
                    onDismiss = { showCustomMessagePreviewDialog = false },
                )
            }
        }
    }
}

fun openPlayStore(packageName: String, context: Context) {
    val uri = Uri.parse("market://details?id=$packageName") // Sustituye com.tuapp con el ID de tu app en Play Store
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    context.startActivity(intent)
}
