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
    // 主要有：aidl、assets、jniLibs、java、kotlin、res、manifest
    // manifest：每个类型下，只能定义一个。参考：https://developer.android.com/build/build-variants
    // 原 main 目录下的，要在最后声明（自动生成的文件，会在这个路径下）

    getByName("main") {

      // basic：基础
      val basicRoot = "basic"// 基础：网络、权限、等
      // basic-network
      val basicNetwork = "$basicRoot/network"
      val basicNetworkRes = "src/$basicNetwork/res"
      // basic-launch icon：启动器图标
      val basicLaunchIcon = "$basicRoot/icon"
      val basicLaunchIconRes = "src/$basicLaunchIcon/res"

      // flavor：定制。可定制内容：应用图标、应用名称、包名？
      val flavorRoot = "flavor"
      val flavorXXX = "$flavorRoot/xxx"
      val flavorXXXRes = "src/$flavorXXX/res"

      // res
      val resList = listOf(basicNetworkRes, basicLaunchIconRes, flavorXXXRes, "src/main/res")
      res.srcDirs(resList)

    }

    // getByName("debug") {
    //   res.srcDirs(resList)
    //   manifest.srcDirs(resList)
    //   // manifest 只能定义一个，可以设置多维度，进行组合
    // }

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