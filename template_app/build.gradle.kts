import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

// val  PROD_PROPS = project.rootDir.resolve("local/keystore.properties")
val PROD_PROPS = gradleLocalProperties(project.rootDir.resolve("local/keystore.properties"),providers)

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

  signingConfigs {
    create("release") {
      setSigningConfigKey(signingConfigs.getByName("release"), PROD_PROPS)
    }

    // signingConfigs.getByName("debug") {
    //   setSigningConfigKey(signingConfigs.getByName("debug"), PROD_PROPS)
    // }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")
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

// 调用 signing.gradle.kts 中定义的 configureSigning 函数
val keystorePropertiesPath = "path/to/your/keystore.properties"
apply(from = project.rootDir.resolve("gradle/sign.gradle.kts"))
// configureSigning(keystorePropertiesPath)

// 定义加载签名文件的方法
fun configureSigning(keystorePropertiesPath: String) {
  val keystorePropertiesFile = file(keystorePropertiesPath)
  val keystoreProperties = Properties()
  keystoreProperties.load(FileInputStream(keystorePropertiesFile))

  android {
    signingConfigs {
      create("release") {
        keyAlias = keystoreProperties["keyAlias"] as String
        keyPassword = keystoreProperties["keyPassword"] as String
        storeFile = file(keystoreProperties["storeFile"] as String)
        storePassword = keystoreProperties["storePassword"] as String
      }
    }

    buildTypes {
      getByName("release") {
        signingConfig = signingConfigs.getByName("release")
      }
    }
  }
}

// 定义设置签名配置的方法
fun setSigningConfigKey(
  config: com.android.build.gradle.internal.dsl.SigningConfig,
  props: Properties?
): com.android.build.gradle.internal.dsl.SigningConfig {
  if (props != null) {
    config.storeFile = props.getProperty("keystore")?.let { file(it) }
    config.storePassword = props.getProperty("store.pass")
    config.keyAlias = props.getProperty("key.alias")
    config.keyPassword = props.getProperty("key.pass")
  }
  return config
}