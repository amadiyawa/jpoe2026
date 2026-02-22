# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# Koin
-keep class org.koin.** { *; }

# Retrofit + OkHttp
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Room
-keep class androidx.room.** { *; }

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-keep class kotlinx.serialization.** { *; }

# Gemini / Google AI
-keep class com.google.ai.** { *; }

# Modèles de données
-keep class com.amadiyawa.feature_personality.domain.model.** { *; }
-keep class com.amadiyawa.feature_personality.data.dto.** { *; }