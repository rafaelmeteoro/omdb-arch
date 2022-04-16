repositories {
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
        google()
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.2")
    implementation("com.adarshr:gradle-test-logger-plugin:3.0.0")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.1.0")
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
