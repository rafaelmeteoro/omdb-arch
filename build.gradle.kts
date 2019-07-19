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

apply(plugin = BuildPlugins.Ids.cobertura)
apply(plugin = BuildPlugins.Ids.coveralls)

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}