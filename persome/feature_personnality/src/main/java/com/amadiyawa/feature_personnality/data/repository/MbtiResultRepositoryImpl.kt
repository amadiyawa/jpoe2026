package com.amadiyawa.feature_personnality.data.repository

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.data.datasource.database.MbtiResultDao
import com.amadiyawa.feature_personnality.data.datasource.database.model.toMbtiResult
import com.amadiyawa.feature_personnality.data.datasource.database.model.toMbtiResultEntityModel
import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import com.amadiyawa.feature_personnality.domain.repository.MbtiResultRepository
import timber.log.Timber

internal class MbtiResultRepositoryImpl(
    private val mbtiResultDao: MbtiResultDao
) : MbtiResultRepository {

    override suspend fun getAllResults(): OperationResult<List<MbtiResult>> {
        return try {
            val results = mbtiResultDao.getAllResults().map { it.toMbtiResult() }
            Timber.d("${results.size} résultats récupérés")
            OperationResult.success(results)
        } catch (e: Exception) {
            Timber.e(e, "Erreur récupération résultats")
            OperationResult.error(e)
        }
    }

    override suspend fun getResultById(id: Int): OperationResult<MbtiResult> {
        return try {
            val result = mbtiResultDao.getResultById(id)?.toMbtiResult()
                ?: return OperationResult.error(Exception("Résultat $id introuvable"))
            OperationResult.success(result)
        } catch (e: Exception) {
            Timber.e(e, "Erreur récupération résultat $id")
            OperationResult.error(e)
        }
    }

    override suspend fun insertResult(result: MbtiResult) {
        try {
            mbtiResultDao.insertResult(result.toMbtiResultEntityModel())
            Timber.d("Résultat sauvegardé : ${result.mbtiType}")
        } catch (e: Exception) {
            Timber.e(e, "Erreur sauvegarde résultat")
        }
    }
}