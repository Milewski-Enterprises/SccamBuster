# ProGuard rules for ScamBuster

# Keep all classes in our package
-keep class com.scambuster.app.** { *; }

# Keep line numbers for crash reporting
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
