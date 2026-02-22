package com.amadiyawa.feature_personnality.presentation

import com.amadiyawa.feature_personnality.presentation.screen.history.HistoryViewModel
import com.amadiyawa.feature_personnality.presentation.screen.questionnaire.QuestionnaireViewModel
import com.amadiyawa.feature_personnality.presentation.screen.result.ResultViewModel
import com.amadiyawa.feature_personnality.presentation.screen.userinfo.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


internal val presentationModule = module {

    viewModel {
        QuestionnaireViewModel(
            getQuestionsUseCase = get(),
            calculateMbtiTypeUseCase = get()
        )
    }

    viewModel {
        UserInfoViewModel(
            getAiDescriptionUseCase = get(),
            saveMbtiResultUseCase = get(),
            mbtiDescriptionLocalDataSource = get()
        )
    }

    viewModel {
        HistoryViewModel(
            getAllResultsUseCase = get()
        )
    }
    viewModel {
        ResultViewModel(
            getAllResultsUseCase = get()
        )
    }
}