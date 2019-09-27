package shishkin.sl.kodeinpsb.sl.provider

import okhttp3.OkHttpClient
import retrofit2.Converter


interface INetProvider<T> : IProvider {
    fun getOkHttpClient(): OkHttpClient

    fun getApiClass(): Class<T>

    fun getBaseUrl(): String

    fun getConverterFactory(): Converter.Factory
}
