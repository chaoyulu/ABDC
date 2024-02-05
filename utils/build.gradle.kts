plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

//apply {
//    this.type(GithubPlugin::class)
//}

android {
    namespace = "com.cyl.utils"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk

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

//    publishing {
//        singleVariant("release")
//        singleVariant("debug")
//    }
}

dependencies {
    implementation(Libs.coreKtx)
    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(Libs.gson)
    implementation(Libs.toaster)
    implementation(Libs.permission)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

afterEvaluate {
    publishing {
        publications {
            register("githubRelease", MavenPublication::class) {
                groupId = PublishInfo.groupId
                artifactId = PublishInfo.utils.artifactId
                version = PublishInfo.utils.version
            }
        }
    }
}