plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
  namespace = "com.georgebindragon.application.sample"
  compileSdk = 30

  defaultConfig {
    applicationId = "com.georgebindragon.application.sample"
    minSdk = 24
    targetSdk = 30
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
    compose = false
  }
  // composeOptions {
  //   kotlinCompilerExtensionVersion = "1.5.12"
  // }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  // 测试库
  testImplementation(libs.testJunit)
  androidTestImplementation(libs.testXSupportRunner)
  androidTestImplementation(libs.testXSupportEspresso)

  api("com.leon.channel:helper:2.0.2")
  implementation("com.hannesdorfmann.mosby3:mvi:3.1.1")

  // 基础工具类
  implementation(libs.baseApplication)
}