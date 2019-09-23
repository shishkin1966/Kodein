package shishkin.sl.kodeinpsb.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.data.Balance


@Dao
interface Dao {
    @Query("SELECT * FROM " + Account.TABLE + " ORDER BY " + Account.Companion.COLUMNS.friendlyName + " ASC")
    fun getAccounts(): List<Account>

    @Query("SELECT " + Account.Companion.COLUMNS.currency + " as currency, sum(" + Account.Companion.COLUMNS.balance + ") as balance FROM " + Account.TABLE + " GROUP BY " + Account.Companion.COLUMNS.currency)
    fun getBalance(): List<Balance>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAccount(account: Account)

}
