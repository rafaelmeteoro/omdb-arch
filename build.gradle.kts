import com.vanniktech.android.junit.jacoco.JunitJacocoExtension

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://jitpack.io")
    }

    dependencies {
        classpath(BuildPlugins.Dependencies.androidSupport)
        classpath(BuildPlugins.Dependencies.kotlinSupport)
        classpath(BuildPlugins.Dependencies.testLogger)
        classpath(BuildPlugins.Dependencies.kotlinxSerialization)
        classpath(BuildPlugins.Dependencies.versions)
        classpath(BuildPlugins.Dependencies.safeArgs)
        classpath(BuildPlugins.Dependencies.ktlint)
        classpath(BuildPlugins.Dependencies.detekt)
        classpath(BuildPlugins.Dependencies.jacocoUnified)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://jitpack.io")
    }
}

apply(plugin = BuildPlugins.Ids.testLogger)
apply(plugin = BuildPlugins.Ids.versions)
apply(plugin = BuildPlugins.Ids.jacocoUnified)

configure<JunitJacocoExtension> {
    jacocoVersion = "0.8.4"
    includeNoLocationClasses = true
}
