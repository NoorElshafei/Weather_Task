object NamceSpace {

    const val applicationId = "com.baims.weathertask"

    object Core {
        private const val core = "com.baims.core"
        const val network = "$core.network"
        const val utils = "$core.utils"
        const val ui = "$core.ui"
        const val uiTest = "$core.uiTest"
    }

    object Feature {
        private const val feature = "com.baims.feature"
        const val weather = "$feature.weather"
    }
}