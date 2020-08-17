import dependencies.UnitTestDependencies.Companion.unitTest

plugins {
    id(BuildPlugins.Ids.kotlinModule)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)

    unitTest { forEachDependency { testImplementation(it) } }
    testImplementation(project(":libraries:coroutines-testutils"))
}