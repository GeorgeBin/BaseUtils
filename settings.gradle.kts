pluginManagement {
  includeBuild("build-logic")

  repositories {
    mavenCentral()
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io") //https://github.com/CymChad/BaseRecyclerViewAdapterHelper 需要
    jcenter()// = http://jcenter.bintray.com
  }
}

rootProject.name = "baseutils"

include(":utils_java")
include(":utils_android")
include(":utils_rx")
include(":base_application")
include(":utils_kotlin")

include(":example_sample")