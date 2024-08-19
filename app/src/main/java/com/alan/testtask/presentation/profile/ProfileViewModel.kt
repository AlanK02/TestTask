package com.alan.testtask.presentation.profile


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alan.testtask.R
import com.alan.testtask.domain.entity.ProfileData
import com.alan.testtask.domain.useCase.GetProfileUseCase
import com.alan.testtask.domain.useCase.UpdateProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> get() = _profileState

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadProfile(phoneNumber: String, context: Context) {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            val result = getProfileUseCase(phoneNumber)
            result.onSuccess { profileData ->
                val zodiacSign = calculateZodiac(birthday = profileData.birthday,context = context)
                _profileState.value = ProfileState.Success(profileData, zodiacSign)
            }.onFailure {
                _profileState.value = ProfileState.Error(it.message ?: "Unknown error")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(profileData: ProfileData, context: Context) {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            val result = updateProfileUseCase(profileData)
            result.onSuccess {
                val zodiacSign = calculateZodiac(birthday = profileData.birthday, context = context)
                _profileState.value = ProfileState.Success(it, zodiacSign)
            }.onFailure {
                _profileState.value = ProfileState.Error(it.message ?: "Unknown error")
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateToServerFormat(date: String, context: Context): String {
        return try {
            val inputFormat = DateTimeFormatter.ofPattern(context.getString(R.string.input_date_format))
            val outputFormat = DateTimeFormatter.ofPattern(context.getString(R.string.output_date_format))

            if (date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                return date
            }
            LocalDate.parse(date, inputFormat).format(outputFormat)
        } catch (e: DateTimeParseException) {
            ""
        }
    }
}
