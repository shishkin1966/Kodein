package shishkin.sl.kotlin.app.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Account.TABLE)
class Account() : Parcelable {
    companion object CREATOR : Parcelable.Creator<Account> {

        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }

        const val TABLE = "Account"

        val PROJECTION = arrayOf(
            COLUMNS.id,
            COLUMNS.openDate,
            COLUMNS.friendlyName,
            COLUMNS.balance,
            COLUMNS.currency
        )

        class COLUMNS {
            companion object {
                const val id = "id"
                const val openDate = "openDate"
                const val friendlyName = "friendlyName"
                const val balance = "balance"
                const val currency = "currency"
            }
        }
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int? = null

    @ColumnInfo(name = COLUMNS.openDate)
    @SerializedName(COLUMNS.openDate)
    var openDate: Long? = null

    @ColumnInfo(name = COLUMNS.friendlyName)
    @SerializedName(COLUMNS.friendlyName)
    var friendlyName: String? = null

    @ColumnInfo(name = COLUMNS.balance)
    @SerializedName(COLUMNS.balance)
    var balance: Double? = 0.00

    @ColumnInfo(name = COLUMNS.currency)
    @SerializedName(COLUMNS.currency)
    var currency: String = Currency.RUR

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        openDate = parcel.readValue(Long::class.java.classLoader) as? Long
        friendlyName = parcel.readString()
        balance = parcel.readValue(Double::class.java.classLoader) as? Double
        currency = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(openDate)
        parcel.writeString(friendlyName)
        parcel.writeValue(balance)
        parcel.writeString(currency)
    }

    override fun describeContents(): Int {
        return 0
    }


}
