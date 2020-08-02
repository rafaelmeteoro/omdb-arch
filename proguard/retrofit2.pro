-dontnote retrofit2.Platform
-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn kotlin.Unit
-dontwarn retrofit2.-KotlinExtensions
-dontwarn javax.annotation.**