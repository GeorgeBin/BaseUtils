import org.jetbrains.kotlin.gradle.plugin.KotlinTargetHierarchy.SourceSetTree.Companion.main

plugins {
  id(libs.plugins.androidApplication.get().pluginId)
  id(libs.plugins.jetbrainsKotlinAndroid.get().pluginId)
}

android {
  namespace = "com.georgebindragon.template.app"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.georgebindragon.template.app"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

  // 自定义资源
  sourceSets {

    val mainRoot = "main"// 原 main 目录
    val basicRoot = "basic"// 基础：网络等
    val basicNetwork = "$basicRoot/network"

    getByName("main") {
      manifest.srcFile("src/$basicRoot/AndroidManifest.xml")
      res.srcDir("src/$basicNetwork/res")

      // 原 main 目录下的，要在最后声明
      res.srcDir("src/$mainRoot/res")
      manifest.srcFile("src/$mainRoot/AndroidManifest.xml")
    }

  }
}

dependencies {

  implementation(libs.core.ktx)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.activity.compose)
  implementation(platform(libs.compose.bom))
  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.material3)
  testImplementation(libs.testJunit)
  androidTestImplementation(libs.junit)
  androidTestImplementation(libs.testXSupportEspresso)
  androidTestImplementation(platform(libs.compose.bom))
  androidTestImplementation(libs.ui.test.junit4)
  debugImplementation(libs.ui.tooling)
  debugImplementation(libs.ui.test.manifest)
}