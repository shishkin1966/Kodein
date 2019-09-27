package microservices.shishkin.example.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class Ticker() : Parcelable {
    companion object CREATOR : Parcelable.Creator<Ticker> {

        override fun createFromParcel(parcel: Parcel): Ticker {
            return Ticker(parcel)
        }

        override fun newArray(size: Int): Array<Ticker?> {
            return arrayOfNulls(size)
        }
    }

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("symbol")
    var symbol: String? = null

    @SerializedName("rank")
    var rank: String? = null

    @SerializedName("price_usd")
    var priceUsd: String? = null

    @SerializedName("price_btc")
    var priceBtc: String? = null

    @SerializedName("24h_volume_usd")
    var volumeUsd: String? = null

    @SerializedName("market_cap_usd")
    var marketCapUsd: String? = null

    @SerializedName("available_supply")
    var availableSupply: String? = null

    @SerializedName("total_supply")
    var totalSupply: String? = null

    @SerializedName("max_supply")
    var maxSupply: String? = null

    @SerializedName("percent_change_1h")
    var percentChange1h: String? = null

    @SerializedName("percent_change_24h")
    var percentChange24h: String? = null

    @SerializedName("percent_change_7d")
    var percentChange7d: String? = null

    @SerializedName("last_updated")
    var lastUpdated: String? = null

    @SerializedName("favorite")
    var favorite: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        symbol = parcel.readString()
        rank = parcel.readString()
        priceUsd = parcel.readString()
        priceBtc = parcel.readString()
        volumeUsd = parcel.readString()
        marketCapUsd = parcel.readString()
        availableSupply = parcel.readString()
        totalSupply = parcel.readString()
        maxSupply = parcel.readString()
        percentChange1h = parcel.readString()
        percentChange24h = parcel.readString()
        percentChange7d = parcel.readString()
        lastUpdated = parcel.readString()
        favorite = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(symbol)
        parcel.writeString(rank)
        parcel.writeString(priceUsd)
        parcel.writeString(priceBtc)
        parcel.writeString(volumeUsd)
        parcel.writeString(marketCapUsd)
        parcel.writeString(availableSupply)
        parcel.writeString(totalSupply)
        parcel.writeString(maxSupply)
        parcel.writeString(percentChange1h)
        parcel.writeString(percentChange24h)
        parcel.writeString(percentChange7d)
        parcel.writeString(lastUpdated)
        parcel.writeInt(favorite)
    }

    override fun describeContents(): Int {
        return 0
    }
}
