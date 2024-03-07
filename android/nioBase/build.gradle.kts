plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("D:\\whFile\\nio-keystore\\niolan.jks")
            storePassword = "xxxd86niolan369"
            keyPassword = "xxxd86niolan369"
            keyAlias = "niolan"
        }
    }
    namespace = "com.xxxd86.niobase"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
        signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    // OpenCV基础库（*必须）
    implementation ("com.github.jenly1314.WeChatQRCode:opencv:2.1.0")
    implementation ("com.github.jenly1314.WeChatQRCode:opencv-armv7a:2.1.0")

// OpenCV的其他ABI（可选），根据你的需要选择想要支持的SO库架构
    implementation ("com.github.jenly1314.WeChatQRCode:opencv-armv64:2.1.0")
    implementation ("com.github.jenly1314.WeChatQRCode:opencv-x86:2.1.0")
    implementation ("com.github.jenly1314.WeChatQRCode:opencv-x86_64:2.1.0")
    // 微信二维码扫码功能（可选）
    // 微信二维码识别功能（可选）
    implementation ("com.github.jenly1314.WeChatQRCode:wechat-qrcode:2.1.0")
    implementation ("com.github.jenly1314.WeChatQRCode:wechat-qrcode-scanning:2.1.0")
    implementation ("com.squareup.retrofit2:retrofit:2.6.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0") //配置使用Gson解析响应数据 可选
    implementation ("com.squareup.retrofit2:adapter-rxjava:2.4.0") //配置支持RxJava 可选
}