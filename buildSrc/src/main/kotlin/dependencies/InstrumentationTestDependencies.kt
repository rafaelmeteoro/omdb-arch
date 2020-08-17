package dependencies

import Libraries

class InstrumentationTestDependencies {

    private val all by lazy {
        listOf(
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
    }

    fun forEachDependency(consumer: (String) -> Unit) =
        all.forEach { consumer.invoke(it) }

    companion object {
        fun instrumentationTest(block: InstrumentationTestDependencies.() -> Unit) =
            InstrumentationTestDependencies().apply(block)
    }
}