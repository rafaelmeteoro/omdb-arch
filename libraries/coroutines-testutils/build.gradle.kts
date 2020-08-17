plugins {
    id(BuildPlugins.Ids.kotlinModule)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesTest)
    implementation(Libraries.coroutinesDebug)
    implementation(Libraries.jUnit)
}