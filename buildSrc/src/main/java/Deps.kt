object Libs {
    // android官方
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"
    const val coreKtx = "androidx.core:core-ktx:1.12.0"
    const val material = "com.google.android.material:material:1.11.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    // 第三方
    const val coil = "io.coil-kt:coil:2.5.0"
    const val glide4 = "com.github.bumptech.glide:glide:4.11.0"
    const val glide4Processor = "com.github.bumptech.glide:compiler:4.11.0"
    const val okhttp = ""
    const val ktor = ""
    const val gson = "com.google.code.gson:gson:2.10.1"
    const val toaster = "com.github.getActivity:Toaster:12.6"
    const val logcat = "com.github.getActivity:Logcat:11.85"
    const val permission = "com.github.getActivity:XXPermissions:18.6"
}


object Versions {
    const val compileSdk = 34
    const val minSdk = 23
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object PublishInfo {
    const val groupId = "com.chaoyulu.utils"

    // utils
    val utils = ItemInfo("utils", "1.0.0")
    val imageLoader = ItemInfo("imageloader", "1.0.0")
    val baseViewBinding = ItemInfo("baseviewbinding", "1.0.0")
}

data class ItemInfo(val artifactId: String, val version: String)