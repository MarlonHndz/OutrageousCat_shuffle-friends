package com.outrageouscat.shufflefriends.ui.screens.aboutUs

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outrageouscat.shufflefriends.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    context: Context,
    modifier: Modifier,
    onBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(stringResource(R.string.about_us_screen_title))
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            Image(
                painter = painterResource(id = R.drawable.outrageous_cat_logo_no_bg),
                contentDescription = stringResource(R.string.content_description_app_logo),
                modifier = Modifier
                    .height(90.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                modifier =
                Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.about_us_outrageous_cat_section_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.W300,
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.about_us_outrageous_cat_slogan),
                fontSize = 13.sp,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.about_us_outrageous_cat_philosophy_text),
                fontSize = 15.sp,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Justify,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.shuffle_friends_icon_background),
                contentDescription = stringResource(R.string.content_description_app_logo),
                modifier = Modifier
                    .height(90.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                modifier =
                Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.app_name),
                fontSize = 32.sp,
                fontWeight = FontWeight.W300,
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.about_us_shuffle_friends_slogan),
                fontSize = 13.sp,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.about_us_shuffle_friends_description_text),
                fontSize = 15.sp,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Justify,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = stringResource(R.string.about_us_shuffle_friends_app_info_label),
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.about_us_app_info),
                fontSize = 15.sp,
                fontWeight = FontWeight.W300,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview
@Composable
fun PreviewAboutUsScreen() {
    AboutUsScreen(
        context = LocalContext.current,
        modifier = Modifier.fillMaxSize(),
        onBack = { }
    )
}