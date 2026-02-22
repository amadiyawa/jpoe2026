package com.amadiyawa.feature_personnality.domain


import com.amadiyawa.feature_personnality.domain.usecase.CalculateMbtiTypeUseCase
import com.amadiyawa.feature_personnality.domain.usecase.GetAiDescriptionUseCase
import com.amadiyawa.feature_personnality.domain.usecase.GetAllResultsUseCase
import com.amadiyawa.feature_personnality.domain.usecase.GetQuestionsUseCase
import com.amadiyawa.feature_personnality.domain.usecase.GetResultByIdUseCase
import com.amadiyawa.feature_personnality.domain.usecase.SaveMbtiResultUseCase
import org.koin.dsl.module

internal val domainModule = module {

    // Pas de dépendance externe → factory suffit
    factory { CalculateMbtiTypeUseCase() }

    factory {
        GetQuestionsUseCase(
            questionRepository = get()
        )
    }

    factory {
        GetAiDescriptionUseCase(
            personalityApiRepository = get()
        )
    }

    factory {
        SaveMbtiResultUseCase(
            mbtiResultRepository = get()
        )
    }

    factory {
        GetAllResultsUseCase(
            mbtiResultRepository = get()
        )
    }

    factory {
        GetResultByIdUseCase(
            mbtiResultRepository = get()
        )
    }
}