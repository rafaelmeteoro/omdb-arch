import com.vanniktech.android.junit.jacoco.JunitJacocoExtension
import io.gitlab.arturbosch.detekt.detekt

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
        classpath(BuildPlugins.Dependencies.cobertura)
        classpath(BuildPlugins.Dependencies.coveralls)
        classpath(BuildPlugins.Dependencies.testLogger)
        classpath(BuildPlugins.Dependencies.kotlinxSerialization)
        classpath(BuildPlugins.Dependencies.jacocoUnified)
        classpath(BuildPlugins.Dependencies.sonarCloud)
        classpath(BuildPlugins.Dependencies.detekt)
        classpath(BuildPlugins.Dependencies.versions)
        classpath(BuildPlugins.Dependencies.safeArgs)
        classpath(BuildPlugins.Dependencies.realm)
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

    apply(plugin = BuildPlugins.Ids.detekt)
    apply(plugin = BuildPlugins.Ids.coveralls)

    detekt {
        config = files("$rootDir/default-detekt-config.yml")
    }
}

apply(plugin = BuildPlugins.Ids.coveralls)
apply(plugin = BuildPlugins.Ids.testLogger)
apply(plugin = BuildPlugins.Ids.jacocoUnified)
apply(plugin = BuildPlugins.Ids.sonarCloud)
apply(plugin = BuildPlugins.Ids.versions)

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}

configure<JunitJacocoExtension> {
    jacocoVersion = "0.8.4"
}