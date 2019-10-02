package dependencies

class InstrumentationTestsDependencies {

    private val all by lazy {
        listOf(
            Libraries.jUnit,
            Libraries.jUnitExt,
            Libraries.jUnitExtKtx,
            Libraries.archCoreTesting,
            Libraries.mockitoKotlin,
            Libraries.androidTestRunner,
            Libraries.espressoCore
        )
    }

    fun forEachDependency(consumer: (String) -> Unit) =
        all.forEach { consumer.invoke(it) }

    companion object {
        fun instrumentationTest(block: InstrumentationTestsDependencies.() -> Unit) =
            InstrumentationTestsDependencies().apply(block)
    }
}