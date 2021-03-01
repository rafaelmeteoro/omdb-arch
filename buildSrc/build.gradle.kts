repositories {
    jcenter()
    google()
    mavenCentral()
    gradlePluginPortal()
}

plugins {
    java
    `kotlin-dsl`
    `java-gradle-plugin`
}

buildscript {
    repositories {
        jcenter()
        google()
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("com.adarshr:gradle-test-logger-plugin:2.1.1")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:9.4.1")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.15.0")
    implementation("com.vanniktech:gradle-android-junit-jacoco-plugin:0.16.0")
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin"))
    implementation(kotlin("android-extensions"))
}

gradlePlugin {
    plugins {
        register("kotlin-module") {
            id = "kotlin-module"
            implementationClass = "plugins.SetupKotlinModulePlugin"
        }

        register("android-module") {
            id = "android-module"
            implementationClass = "plugins.SetupAndroidModulePlugin"
        }
    }
}
