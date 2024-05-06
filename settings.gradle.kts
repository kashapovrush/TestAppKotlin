include(":core")


pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
//enableFeaturePreview("VERSION_CATALOGS")
rootProject.name = "goDriveKotlin"
include (":app")
include(":features-mobile")
include(":features-mobile:sign-up-feature")
include(":features-mobile:palette")
include(":features-mobile:enter-code-feature")
include(":features-mobile:main-chat-feature")
include(":features-mobile:free-chat-feature")
include(":features-mobile:profile-feature")
include(":features-mobile:profile-free-feature")
include(":features-mobile:settings-feature")
