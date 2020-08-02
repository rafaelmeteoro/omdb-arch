package dependencies

class ModulesDependencies {

    companion object {
        private const val KEY_DEPENDENCIES = "dependencies"
        private const val KEY_TEST_DEPENDENCIES = "testDependencies"
        private const val KEY_ANDROID_TEST_DEPENDENCIES = "androidTestDependencies"
        private const val KEY_COMPILERS = "compilers"

        fun moduleDependencies(block: ModulesDependencies.() -> Unit) =
            ModulesDependencies().apply(block)
    }

    fun forEachDependencies(map: Map<String, List<String>>, consumer: (String) -> Unit) =
        map[KEY_DEPENDENCIES]?.forEach { consumer.invoke(it) }

    fun forEachCompilers(map: Map<String, List<String>>, consumer: (String) -> Unit) =
        map[KEY_COMPILERS]?.forEach { consumer.invoke(it) }

    fun forEachTestDependencies(map: Map<String, List<String>>, consumer: (String) -> Unit) =
        map[KEY_TEST_DEPENDENCIES]?.forEach { consumer.invoke(it) }

    fun forEachAndroidTestDependencies(map: Map<String, List<String>>, consumer: (String) -> Unit) =
        map[KEY_ANDROID_TEST_DEPENDENCIES]?.forEach { consumer.invoke(it) }

    val domain by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.rxJava,
                    Libraries.rxKotlin,
                    Libraries.lifecycleCommon,
                    Libraries.lifecycleJava8,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.timber
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor
                )
            ),
            Pair(
                KEY_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.assertj,
                    Libraries.mockitoKotlin
                )
            )
        )
    }

    val actions by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.coreAndroidx
                )
            )
        )
    }

    val persistence by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.rxJava,
                    Libraries.rxKotlin,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.roomRuntime,
                    Libraries.roomKtx,
                    Libraries.roomRxJava2,
                    Libraries.archCoreCommon,
                    Libraries.archCoreRuntime
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor,
                    Libraries.roomCompiler
                )
            ),
            Pair(
                KEY_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.assertj,
                    Libraries.mockitoKotlin,
                    Libraries.mockWebServer,
                    Libraries.roomTesting
                )
            )
        )
    }

    val rest by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.kotlinSerialization,
                    Libraries.okhttp,
                    Libraries.okhttpLogger,
                    Libraries.retrofit,
                    Libraries.retrofitRxAdapter,
                    Libraries.retrofitScalars,
                    Libraries.retrofitGsonConverter,
                    Libraries.retrofitKotlinSerialization,
                    Libraries.gson,
                    Libraries.rxJava,
                    Libraries.rxKotlin,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.timber
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor
                )
            ),
            Pair(
                KEY_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.assertj,
                    Libraries.mockitoKotlin,
                    Libraries.mockWebServer
                )
            )
        )
    }

    val uicomponents by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.appCompat,
                    Libraries.materialDesign,
                    Libraries.recyclerView,
                    Libraries.lifecycleRuntime
                )
            )
        )
    }

    val onboarding by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.appCompat,
                    Libraries.materialDesign,
                    Libraries.constraintLayout,
                    Libraries.lifecycleRuntime,
                    Libraries.coroutinesCore,
                    Libraries.coroutinesAndroid,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.timber
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor
                )
            )
        )
    }

    val home by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.appCompat,
                    Libraries.cardView,
                    Libraries.recyclerView,
                    Libraries.materialDesign,
                    Libraries.coreAndroidx,
                    Libraries.constraintLayout,
                    Libraries.lifecycleCommon,
                    Libraries.lifecycleJava8,
                    Libraries.lifecycleViewModel,
                    Libraries.lifecycleExtensions,
                    Libraries.rxJava,
                    Libraries.rxKotlin,
                    Libraries.rxAndroid,
                    Libraries.picasso,
                    Libraries.coil,
                    Libraries.coilBase,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.timber
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor
                )
            ),
            Pair(
                KEY_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.assertj,
                    Libraries.mockitoKotlin,
                    Libraries.mockWebServer,
                    Libraries.roomTesting
                )
            )
        )
    }

    val favorites by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.appCompat,
                    Libraries.cardView,
                    Libraries.recyclerView,
                    Libraries.materialDesign,
                    Libraries.coreAndroidx,
                    Libraries.constraintLayout,
                    Libraries.lifecycleCommon,
                    Libraries.lifecycleJava8,
                    Libraries.lifecycleViewModel,
                    Libraries.lifecycleExtensions,
                    Libraries.navigationFragmentKtx,
                    Libraries.navigationRuntimeKtx,
                    Libraries.navigationCommontKtx,
                    Libraries.navigationUiKtx,
                    Libraries.rxJava,
                    Libraries.rxKotlin,
                    Libraries.rxAndroid,
                    Libraries.picasso,
                    Libraries.coil,
                    Libraries.coilBase,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.roomRuntime,
                    Libraries.roomKtx,
                    Libraries.roomRxJava2,
                    Libraries.timber
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor,
                    Libraries.roomCompiler
                )
            ),
            Pair(
                KEY_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.assertj,
                    Libraries.mockitoKotlin,
                    Libraries.mockWebServer,
                    Libraries.roomTesting
                )
            )
        )
    }

    val details by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.appCompat,
                    Libraries.cardView,
                    Libraries.recyclerView,
                    Libraries.materialDesign,
                    Libraries.coreAndroidx,
                    Libraries.constraintLayout,
                    Libraries.lifecycleCommon,
                    Libraries.lifecycleJava8,
                    Libraries.lifecycleViewModel,
                    Libraries.lifecycleExtensions,
                    Libraries.rxJava,
                    Libraries.rxKotlin,
                    Libraries.rxAndroid,
                    Libraries.picasso,
                    Libraries.coil,
                    Libraries.coilBase,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.roomRuntime,
                    Libraries.roomKtx,
                    Libraries.roomRxJava2,
                    Libraries.timber
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor,
                    Libraries.roomCompiler
                )
            ),
            Pair(
                KEY_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.assertj,
                    Libraries.mockitoKotlin,
                    Libraries.mockWebServer,
                    Libraries.roomTesting
                )
            )
        )
    }

    val app by lazy {
        mapOf(
            Pair(
                KEY_DEPENDENCIES, listOf(
                    Libraries.kotlinStdLib,
                    Libraries.appCompat,
                    Libraries.cardView,
                    Libraries.recyclerView,
                    Libraries.materialDesign,
                    Libraries.coreAndroidx,
                    Libraries.constraintLayout,
                    Libraries.lifecycleCommon,
                    Libraries.lifecycleJava8,
                    Libraries.lifecycleViewModel,
                    Libraries.lifecycleExtensions,
                    Libraries.rxJava,
                    Libraries.rxKotlin,
                    Libraries.rxAndroid,
                    Libraries.okhttp,
                    Libraries.okhttpLogger,
                    Libraries.retrofit,
                    Libraries.retrofitRxAdapter,
                    Libraries.retrofitScalars,
                    Libraries.retrofitKotlinSerialization,
                    Libraries.retrofitGsonConverter,
                    Libraries.gson,
                    Libraries.picasso,
                    Libraries.coil,
                    Libraries.coilBase,
                    Libraries.stetho,
                    Libraries.stethoOkHttp,
                    Libraries.dagger,
                    Libraries.daggerAndroid,
                    Libraries.daggerAndroidSupport,
                    Libraries.roomRuntime,
                    Libraries.roomKtx,
                    Libraries.roomRxJava2,
                    Libraries.timber
                )
            ),
            Pair(
                KEY_COMPILERS, listOf(
                    Libraries.daggerCompiler,
                    Libraries.daggerAndroidProcessor,
                    Libraries.roomCompiler
                )
            ),
            Pair(
                KEY_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.assertj,
                    Libraries.mockitoKotlin,
                    Libraries.mockWebServer,
                    Libraries.roomTesting
                )
            ),
            Pair(
                KEY_ANDROID_TEST_DEPENDENCIES, listOf(
                    Libraries.jUnit,
                    Libraries.jUnitExt,
                    Libraries.jUnitExtKtx,
                    Libraries.archCoreTesting,
                    Libraries.mockitoAndroid,
                    Libraries.androidTestCore,
                    Libraries.androidTestCoreKtx,
                    Libraries.androidTestRunner,
                    Libraries.espressoCore,
                    Libraries.barista
                )
            )
        )
    }
}