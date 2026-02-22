package com.amadiyawa.feature_personnality.data.repository

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.data.datasource.remote.PersonalityApiDataSource
import com.amadiyawa.feature_personnality.domain.model.UserInfo
import com.amadiyawa.feature_personnality.domain.repository.PersonalityApiRepository
import timber.log.Timber

internal class PersonalityApiRepositoryImpl(
    private val personalityApiDataSource: PersonalityApiDataSource
) : PersonalityApiRepository {

    override suspend fun getAiDescription(
        mbtiType: String,
        userInfo: UserInfo
    ): OperationResult<String> {
        return try {
            val response = personalityApiDataSource.getAiDescription(
                mbtiType = mbtiType,
                userInfo = userInfo
            )
            Timber.d("Description IA reçue pour $mbtiType")
            OperationResult.success(response.description)
        } catch (e: Exception) {
            Timber.w("API indisponible")
            OperationResult.error(throwable = e)
        }
    }
}