package com.amadiyawa.feature_base.presentation.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.amadiyawa.feature_base.domain.permission.PermissionHandler
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume
import android.net.Uri
import android.provider.Settings

/**
 * Implementation of [PermissionHandler] using Activity Result API.
 *
 * @param activity The activity context used for permission requests
 */
class PermissionHandlerImpl(
    private val activity: ComponentActivity
) : PermissionHandler {

    private val context: Context = activity.applicationContext

    override suspend fun requestPermissions(
        vararg permissions: String
    ): Map<String, Boolean> = suspendCancellableCoroutine { continuation ->

        Timber.d("Requesting permissions: ${permissions.joinToString()}")

        // Check already granted
        val notGranted = permissions.filter { !isPermissionGranted(it) }

        if (notGranted.isEmpty()) {
            Timber.d("All permissions already granted")
            continuation.resume(permissions.associateWith { true })
            return@suspendCancellableCoroutine
        }

        Timber.d("Permissions to request: ${notGranted.joinToString()}")

        val launcher = activity.activityResultRegistry.register(
            "permission_request_${System.currentTimeMillis()}",
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            Timber.d("Permission results: $results")

            val allResults = permissions.associateWith { permission ->
                results[permission] ?: isPermissionGranted(permission)
            }

            if (continuation.isActive) {
                continuation.resume(allResults)
            }
        }

        continuation.invokeOnCancellation {
            launcher.unregister()
        }

        launcher.launch(notGranted.toTypedArray())
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun arePermissionsGranted(vararg permissions: String): Boolean {
        return permissions.all { isPermissionGranted(it) }
    }

    override fun shouldShowRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            permission
        )
    }

    override fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}