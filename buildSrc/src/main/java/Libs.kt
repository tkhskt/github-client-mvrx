object Libs {
    object Versions {
        val rxKotlin = "2.3.0"
        val okHttp = "3.14.1"
        val rxAndroid = "2.1.1"
        val threeTenAbp = "1.2.0"
        val timber = "4.7.1"
        val stetho = "1.5.1"
        val retrofit = "2.5.0"
        val moshi = "1.8.0"
        val koin = "2.0.0-rc-2"
    }

    val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val threeTenAbp = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenAbp}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val stetho = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinAndroidxScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
}