# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class com.margin.app.**$$serializer {
    *** INSTANCE;
}
-keep,includedescriptorclasses class com.margin.app.**$$serializer { *; }
-keepclassmembers class com.margin.app.** {
    *** Companion;
}

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
