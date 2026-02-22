package com.amadiyawa.feature_personnality

import com.amadiyawa.feature_personnality.data.dataModule
import com.amadiyawa.feature_personnality.domain.domainModule
import com.amadiyawa.feature_personnality.presentation.presentationModule

val featurePersonalityModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)