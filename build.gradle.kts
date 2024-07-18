buildscript {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
    }
  dependencies {
    classpath(libs.kotlin.gradle.plugin)
  }

  dependencies {
        classpath(libs.android.gradlePlugin)
        // classpath(libs.kotlin.gradlePlugin)
        // classpath(libs.secrets.gradlePlugin)
        // classpath(libs.benchmark.gradlePlugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}

// apply from:"dependenceConfig.gradle"
//
// allprojects {
//     plugins.withId("com.vanniktech.maven.publish") {
//         mavenPublish {
//             sonatypeHost = "S01"
//         }
//     }
// }
//
// classpath 'com.vanniktech:gradle-maven-publish-plugin:0.19.0'