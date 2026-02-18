package com.amadiyawa.feature_base

import com.amadiyawa.feature_base.data.dataModule
import com.amadiyawa.feature_base.domain.domainModule
import com.amadiyawa.feature_base.presentation.permissionModule
import com.amadiyawa.feature_base.presentation.presentationModule

val featureBaseModule = listOf(
    presentationModule,
    permissionModule,
    domainModule,
    dataModule
)