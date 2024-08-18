package com.alan.testtask.presentation.profile

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.alan.testtask.R
import com.alan.testtask.domain.entity.ProfileData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileData: ProfileData,
    onEditProfileClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            val painter = rememberAsyncImagePainter(profileData.avatar?.filename)

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.profile_icon),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${stringResource(R.string.name)}: ${profileData.name}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stringResource(R.string.username)}: ${profileData.username}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onEditProfileClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.edit_profile))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = stringResource(R.string.phone_icon),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Phone: ${profileData.phone}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.city_icon),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${stringResource(R.string.city)}: ${profileData.city}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.birthday_icon),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${stringResource(R.string.birthday)}: ${profileData.birthday}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.zodiac_icon),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${stringResource(R.string.zodiac)}: ${
                        profileData.birthday?.let {
                            calculateZodiac(
                                it,
                                context = LocalContext.current
                            )
                        }
                    }"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${stringResource(R.string.about_me)}: ${profileData.status}")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun calculateZodiac(birthday: String?, context: Context): String {
    if (birthday.isNullOrEmpty()) {
        return context.getString(R.string.unknown)
    }

    return try {
        val formatter = DateTimeFormatter.ofPattern(context.getString(R.string.output_date_format))
        val date = LocalDate.parse(birthday, formatter)

        val day = date.dayOfMonth
        val month = date.monthValue

        when (month) {
            1 -> if (day < 20) context.getString(R.string.capricom) else context.getString(R.string.aquarius)
            2 -> if (day < 19) context.getString(R.string.aquarius) else context.getString(R.string.pisces)
            3 -> if (day < 21) context.getString(R.string.pisces) else context.getString(R.string.aries)
            4 -> if (day < 20) context.getString(R.string.aries) else context.getString(R.string.taurus)
            5 -> if (day < 21) context.getString(R.string.taurus) else context.getString(R.string.gemini)
            6 -> if (day < 21) context.getString(R.string.gemini) else context.getString(R.string.cancer)
            7 -> if (day < 23) context.getString(R.string.cancer) else context.getString(R.string.leo)
            8 -> if (day < 23) context.getString(R.string.leo) else context.getString(R.string.virgo)
            9 -> if (day < 23) context.getString(R.string.virgo) else context.getString(R.string.libra)
            10 -> if (day < 23) context.getString(R.string.libra) else context.getString(R.string.scorpio)
            11 -> if (day < 22) context.getString(R.string.scorpio) else context.getString(R.string.saggitarius)
            12 -> if (day < 22) context.getString(R.string.saggitarius) else context.getString(R.string.capricom)
            else -> context.getString(R.string.unknown)
        }
    } catch (e: DateTimeParseException) {
        context.getString(R.string.unknown)
    }
}
