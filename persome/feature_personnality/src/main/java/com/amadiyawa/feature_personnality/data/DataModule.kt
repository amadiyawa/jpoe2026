package com.amadiyawa.feature_personnality.data

import androidx.room.Room
import com.amadiyawa.feature_personnality.data.datasource.database.MbtiDescriptionLocalDataSource
import com.amadiyawa.feature_personnality.data.datasource.database.PersonalityDatabase
import com.amadiyawa.feature_personnality.data.datasource.database.QuestionLocalDataSource
import com.amadiyawa.feature_personnality.data.datasource.remote.PersonalityApiDataSource
import com.amadiyawa.feature_personnality.data.datasource.remote.PersonalityApiService
import com.amadiyawa.feature_personnality.data.repository.MbtiResultRepositoryImpl
import com.amadiyawa.feature_personnality.data.repository.PersonalityApiRepositoryImpl
import com.amadiyawa.feature_personnality.data.repository.QuestionRepositoryImpl
import com.amadiyawa.feature_personnality.domain.repository.MbtiResultRepository
import com.amadiyawa.feature_personnality.domain.repository.PersonalityApiRepository
import com.amadiyawa.feature_personnality.domain.repository.QuestionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {
    // ─── BASE DE DONNÉES ROOM ──────────────────────────────
    single {
        Room.databaseBuilder(
            androidContext(),
            PersonalityDatabase::class.java,
            "personality_database"
        ).build()
    }

    // ─── DAOs ──────────────────────────────────────────────
    single { get<PersonalityDatabase>().questionDao() }
    single { get<PersonalityDatabase>().mbtiResultDao() }

    // ─── DATA SOURCES ──────────────────────────────────────

    // Local : charge les questions depuis le JSON
    single {
        QuestionLocalDataSource(
            context = androidContext(),
            questionDao = get()
        )
    }

    // Remote : appels vers le backend NestJS
    single {
        get<Retrofit>().create(PersonalityApiService::class.java)
    }

    single {
        PersonalityApiDataSource(
            personalityApiService = get()
        )
    }

    // ─── REPOSITORIES ──────────────────────────────────────
    single<QuestionRepository> {
        QuestionRepositoryImpl(
            questionDao = get(),
            questionLocalDataSource = get()
        )
    }

    single<MbtiResultRepository> {
        MbtiResultRepositoryImpl(
            mbtiResultDao = get()
        )
    }

    single<PersonalityApiRepository> {
        PersonalityApiRepositoryImpl(
            personalityApiDataSource = get()
        )
    }

    single {
        MbtiDescriptionLocalDataSource(
            context = androidContext()
        )
    }
}