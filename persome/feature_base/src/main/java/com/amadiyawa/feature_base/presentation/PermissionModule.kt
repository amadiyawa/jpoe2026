package com.amadiyawa.feature_base.presentation

import androidx.activity.ComponentActivity
import com.amadiyawa.feature_base.domain.permission.PermissionHandler
import com.amadiyawa.feature_base.presentation.permission.PermissionHandlerImpl
import org.koin.dsl.module

internal val permissionModule = module {

    // PermissionHandler - Scoped with Activity
    factory<PermissionHandler> { params ->
        val activity = params.get<ComponentActivity>()
        PermissionHandlerImpl(activity)
    }
}