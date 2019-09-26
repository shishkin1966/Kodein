package microservices.shishkin.example.net


import io.reactivex.Single
import microservices.shishkin.example.data.Ticker
import retrofit2.http.GET

interface NetApi {
    @get:GET("v1/ticker/")
    val tickers: Single<List<Ticker>>
}
