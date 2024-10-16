plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.georgebindragon.base.app"
  compileSdk = 34

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
  // 测试库
  testImplementation(libs.testJunit)
  androidTestImplementation(libs.testXSupportRunner)
  androidTestImplementation(libs.testXSupportEspresso)

  //内存泄漏检测
  debugApi(libs.leakcanary)
  debugApi("cat.ereza:customactivityoncrash:2.3.0")// crash提示，只在debug版本上用，便于查看崩溃内容

  //--------------基础库--------------

  //64k支持
  api(libs.xMultidex)

  // 基础工具类
  api(libs.utilsjava)
  api(libs.utilsRx)
  api(libs.utilsandroid)

  //响应式
  api(libs.rxJava)
  api(libs.rxAndroid)
  api(libs.rxrelay)

  //--------------生命周期--------------

  api(libs.phoenix)

  //--------------UI库--------------

  //AndroidX
  api(libs.supportXAppcompat)
  api(libs.supportXRecyclerView)

  //腾讯 UI库
  api(libs.qmuiUi)
  api(libs.qmuiLint)
  api(libs.qmuiLint2)
  api(libs.qmuiArch)
  api(libs.qmuiArchAnnotation)

  //RecyclerView Adapter
  api(libs.cymChadBaseAdapter)

  //--------------网络--------------

  api(libs.retrofit2Retrofit)
  api(libs.retrofit2GsonConverter)
}