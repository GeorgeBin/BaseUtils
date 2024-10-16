plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.georgebindragon.application.sample"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.georgebindragon.application.sample"
    minSdk = 21
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

  // Java & Kotlin 版本 https://mp.weixin.qq.com/s/Myk26xmD675pNXaUUWhv8g

  // 使用 Java 11：desugaring 支持的最高版本
  // Java 环境：gradle 运行、Java 源代码编译、Kotlin 源代码编译

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11 // 源码 Java 版本
    targetCompatibility = JavaVersion.VERSION_11 // 编译 Java 版本
  }

  // Kotlin 源代码编译
  kotlinOptions {
    jvmTarget = "11" // 编译 Java 版本
  }

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(11))
    }
  }

  kotlin {
    jvmToolchain(11)
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
  implementation(libs.utilsjava)
  implementation(libs.utilsRx)
  implementation(libs.utilsandroid)
  implementation(libs.baseApplication)
}