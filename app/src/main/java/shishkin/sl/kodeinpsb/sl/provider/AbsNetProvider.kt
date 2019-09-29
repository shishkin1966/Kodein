package shishkin.sl.kodeinpsb.sl.provider

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import shishkin.sl.kodeinpsb.sl.request.IRequest
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.task.NetExecutor
import java.util.concurrent.TimeUnit


abstract class AbsNetProvider<T> : INetProvider<T> {

    private val CONNECT_TIMEOUT: Long = 30 // 30 sec
    private val READ_TIMEOUT: Long = 10 // 10 min
    private val retrofit: Retrofit
    private var api: T

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .addConverterFactory(getConverterFactory())
            .build()
        api = retrofit.create<T>(getApiClass())
    }

    fun getApi(): T {
        return api
    }

    override fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    override fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .followRedirects(false)
            .followSslRedirects(false)
            .retryOnConnectionFailure(false)
        return builder.build()
    }

    override fun request(request: IRequest) {
        if (isValid()) {
            val executor = ApplicationSpecialist.serviceLocator?.get<NetExecutor>(NetExecutor.NAME)
            executor?.execute(request)
        }
    }

    override fun isPersistent(): Boolean {
        return false
    }

    override fun onUnRegister() {
    }

    override fun onRegister() {
    }

    override fun stop() {
    }

    override fun isValid(): Boolean {
        return true
    }
}
