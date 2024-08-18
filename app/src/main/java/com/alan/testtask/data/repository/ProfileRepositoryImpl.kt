package com.alan.testtask.data.repository

import com.alan.testtask.data.local.db.ProfileDao
import com.alan.testtask.data.mapper.mapDomainToRequest
import com.alan.testtask.data.mapper.mapResponseToDomain
import com.alan.testtask.data.mapper.mapResponseToEntity
import com.alan.testtask.data.mapper.mapToDomain
import com.alan.testtask.data.mapper.mapToEntity
import com.alan.testtask.data.network.api.ApiFactory.Companion.BASE_URL
import com.alan.testtask.data.network.api.ApiService
import com.alan.testtask.domain.entity.ProfileData
import com.alan.testtask.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val profileDao: ProfileDao
) : ProfileRepository {

    override suspend fun getProfile(phone: String): Result<ProfileData> {
        val formattedPhone = formatPhone(phone)
        return try {
            val localProfile = profileDao.getProfileByPhone(formattedPhone)
            if (localProfile != null) {
                Result.success(localProfile.mapToDomain())
            } else {
                val response = apiService.getProfile()
                if (response.isSuccessful) {
                    response.body()?.let {
                        profileDao.insertProfile(it.mapResponseToEntity(formattedPhone))
                        Result.success(it.mapResponseToDomain())
                    } ?: Result.failure(Throwable("Empty response body"))
                } else {
                    Result.failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(profileData: ProfileData): Result<ProfileData> {
        val formattedPhone = profileData.phone?.let { formatPhone(it) }
        return try {
            val response = apiService.updateProfile(profileData.mapDomainToRequest())
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val currentProfile = formattedPhone?.let { profileDao.getProfileByPhone(it) }
                    val newAvatarFilename = if (body.avatars?.bigAvatar.isNullOrEmpty()) {
                        currentProfile?.avatarFilename
                    } else {
                        "$BASE_URL/${body.avatars.bigAvatar}"
                    }
                    val dbEntity = formattedPhone?.let { it1 ->
                        profileData.mapToEntity()?.copy(
                            phone = it1,
                            avatarFilename = newAvatarFilename
                        )
                    }
                    if (dbEntity != null) {
                        profileDao.insertProfile(dbEntity)
                    }
                    dbEntity?.let { it1 ->
                        Result.success(it1.mapToDomain())
                    } ?: Result.failure(Throwable("Failed to map entity"))
                } ?: Result.failure(Throwable("Empty response body"))
            } else {
                Result.failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    private fun formatPhone(phone: String): String {
        return if (!phone.startsWith("+")) {
            "+$phone"
        } else {
            phone
        }
    }
}
