// feature_onboarding/presentation/components/PermissionsBlockedDialog.kt

package com.amadiyawa.feature_onboarding.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.amadiyawa.feature_base.presentation.compose.composable.AppTextButton
import com.amadiyawa.feature_base.presentation.compose.composable.FilledButton
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyMedium
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodySmall
import com.amadiyawa.feature_base.presentation.compose.composable.TextHeadlineSmall
import com.amadiyawa.onboarding.R

/**
 * Compact dialog shown when permissions are permanently blocked.
 * Guides user to app settings to manually grant permissions.
 */
@Composable
fun PermissionsBlockedDialog(
    blockedPermissions: List<String>,
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    val groupedPermissions = groupBlockedPermissions(blockedPermissions)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.errorContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Block,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Titre compact
                TextHeadlineSmall(
                    text = stringResource(R.string.permissions_blocked_title),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Message compact
                TextBodyMedium(
                    text = stringResource(R.string.permissions_blocked_message),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    groupedPermissions.forEach { (groupKey, _) ->
                        Icon(
                            imageVector = getPermissionIcon(groupKey),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.error
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        TextBodySmall(
                            text = getPermissionName(groupKey),
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Medium
                        )

                        if (groupKey != groupedPermissions.keys.last()) {
                            TextBodySmall(
                                text = " • ",
                                modifier = Modifier.padding(horizontal = 4.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextBodySmall(
                    text = stringResource(R.string.permissions_blocked_instructions),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Boutons compacts
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppTextButton(
                        onClick = onDismiss,
                        text = stringResource(R.string.cancel)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    FilledButton(
                        onClick = onOpenSettings,
                        text = stringResource(R.string.open_settings)
                    )
                }
            }
        }
    }
}

/**
 * Groups similar permissions together (e.g., SEND_SMS + READ_SMS → "SMS").
 */
private fun groupBlockedPermissions(permissions: List<String>): Map<String, List<String>> {
    val groups = mutableMapOf<String, MutableList<String>>()

    permissions.forEach { permission ->
        val groupKey = when {
            permission.contains("SMS") -> "SMS"
            permission.contains("PHONE") -> "PHONE"
            else -> permission
        }

        groups.getOrPut(groupKey) { mutableListOf() }.add(permission)
    }

    return groups
}

@Composable
private fun getPermissionIcon(groupKey: String): ImageVector {
    return when (groupKey) {
        "SMS" -> Icons.Default.Sms
        "PHONE" -> Icons.Default.PhoneAndroid
        else -> Icons.Outlined.Block
    }
}

@Composable
private fun getPermissionName(groupKey: String): String {
    return when (groupKey) {
        "SMS" -> stringResource(R.string.permission_sms_title)
        "PHONE" -> stringResource(R.string.permission_phone_title)
        else -> groupKey
    }
}