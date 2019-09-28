package microservices.shishkin.example.net


import io.reactivex.Single
import retrofit2.http.GET
import shishkin.sl.kodeinpsb.app.data.Ticker

interface NetApi {
    @get:GET("v1/ticker/")
    val tickers: Single<List<Ticker>>
}
