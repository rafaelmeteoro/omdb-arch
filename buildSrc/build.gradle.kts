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
    implementation("com.adarshr:gradle-test-logger-plugin:2.1.0")
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin"))
    implementation(kotlin("android-extensions"))
}