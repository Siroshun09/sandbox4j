pluginManagement {
    includeBuild("build-logic")

    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "sandbox4j"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

val subProjectPrefix = rootProject.name.lowercase(java.util.Locale.ENGLISH)

sequenceOf(
    "api",
    "core"
).forEach {
    include("$subProjectPrefix-$it")
    project(":$subProjectPrefix-$it").projectDir = file(it)
}
