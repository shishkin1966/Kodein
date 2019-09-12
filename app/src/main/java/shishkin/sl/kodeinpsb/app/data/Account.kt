package shishkin.sl.kodeinpsb.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Account.TABLE)
class Account() : AbsEntity() {
    companion object {

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
    var id: Long? = 0

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
    var currency: String? = Currency.RUR

}
