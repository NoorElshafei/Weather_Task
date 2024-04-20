@file:Suppress("UnstableApiUsage")

include(":core:ui")


include(":core:utils")


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")

    }
}

rootProject.name = "Weather Task"
include(":app")
include(":core")
include(":core:Network")

