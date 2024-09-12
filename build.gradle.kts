import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

plugins.withType<NodeJsRootPlugin> {
    configure<NodeJsRootExtension> {
        version = "22.7.0"
        download = false
//        downloadBaseUrl = "https://mirrors.aliyun.com/nodejs-release/"
    }
}