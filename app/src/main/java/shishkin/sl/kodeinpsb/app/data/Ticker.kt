package microservices.shishkin.example.data

import com.google.gson.annotations.SerializedName


class Ticker {
    @SerializedName("id")
    private val id: String? = null

    @SerializedName("name")
    private val name: String? = null

    @SerializedName("symbol")
    private val symbol: String? = null

    @SerializedName("rank")
    private val rank: String? = null

    @SerializedName("price_usd")
    private val priceUsd: String? = null

    @SerializedName("price_btc")
    private val priceBtc: String? = null

    @SerializedName("24h_volume_usd")
    private val volumeUsd: String? = null

    @SerializedName("market_cap_usd")
    private val marketCapUsd: String? = null

    @SerializedName("available_supply")
    private val availableSupply: String? = null

    @SerializedName("total_supply")
    private val totalSupply: String? = null

    @SerializedName("max_supply")
    private val maxSupply: String? = null

    @SerializedName("percent_change_1h")
    private val percentChange1h: String? = null

    @SerializedName("percent_change_24h")
    private val percentChange24h: String? = null

    @SerializedName("percent_change_7d")
    private val percentChange7d: String? = null

    @SerializedName("last_updated")
    private val lastUpdated: String? = null

    @SerializedName("favorite")
    private val favorite: Int = 0
}
