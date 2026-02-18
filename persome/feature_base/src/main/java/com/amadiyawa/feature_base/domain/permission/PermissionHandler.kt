package com.amadiyawa.feature_base.domain.permission

/**
 * Interface for handling runtime permissions in Android.
 */
interface PermissionHandler {
    /**
     * Request multiple permissions at once.
     *
     * @param permissions Array of permission strings to request
     * @return Map of permission string to boolean (true if granted, false otherwise)
     */
    suspend fun requestPermissions(vararg permissions: String): Map<String, Boolean>

    /**
     * Check if a specific permission is granted.
     *
     * @param permission Permission string to check
     * @return true if granted, false otherwise
     */
    fun isPermissionGranted(permission: String): Boolean

    /**
     * Check if multiple permissions are granted.
     *
     * @param permissions Array of permission strings to check
     * @return true if all permissions are granted, false otherwise
     */
    fun arePermissionsGranted(vararg permissions: String): Boolean

    /**
    * Check if permission rationale should be shown.
    * Returns true if user can still be asked for permission.
    * Returns false if "Don't ask again" was checked.
    */
    fun shouldShowRationale(permission: String): Boolean

    /**
     * Open app settings page for user to manually grant permissions.
     */
    fun openAppSettings()
}