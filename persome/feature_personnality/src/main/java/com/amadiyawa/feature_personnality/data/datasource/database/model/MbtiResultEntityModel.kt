package com.amadiyawa.feature_personnality.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import com.amadiyawa.feature_personnality.domain.model.UserInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "mbti_results")
@TypeConverters(UserInfoTypeConverter::class)
internal data class MbtiResultEntityModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userInfo: UserInfo,          // Sérialisé en JSON via TypeConverter
    val mbtiType: String,
    val aiDescription: String?,
    val staticDescription: String,
    val createdDate: Long
)

// Entity → Domain
internal fun MbtiResultEntityModel.toMbtiResult() = MbtiResult(
    id = id,
    userInfo = userInfo,
    mbtiType = mbtiType,
    aiDescription = aiDescription,
    staticDescription = staticDescription,
    createdDate = createdDate
)

// Domain → Entity
internal fun MbtiResult.toMbtiResultEntityModel() = MbtiResultEntityModel(
    userInfo = userInfo,
    mbtiType = mbtiType,
    aiDescription = aiDescription,
    staticDescription = staticDescription,
    createdDate = createdDate
)

// Convertit UserInfo en String pour Room
internal class UserInfoTypeConverter {
    @TypeConverter
    fun userInfoToString(userInfo: UserInfo): String {
        return Json.encodeToString(userInfo)
    }

    @TypeConverter
    fun stringToUserInfo(value: String): UserInfo {
        return Json.decodeFromString(value)
    }
}