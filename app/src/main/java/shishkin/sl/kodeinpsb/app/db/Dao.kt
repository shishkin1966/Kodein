package shishkin.sl.kodeinpsb.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.data.Balance


@Dao
interface Dao {
    @Query("SELECT * FROM " + Account.TABLE + " ORDER BY " + Account.CREATOR.COLUMNS.friendlyName + " ASC")
    fun getAccounts(): List<Account>

    @Query("SELECT " + Account.CREATOR.COLUMNS.currency + " as currency, sum(" + Account.CREATOR.COLUMNS.balance + ") as balance FROM " + Account.TABLE + " GROUP BY " + Account.CREATOR.COLUMNS.currency)
    fun getBalance(): List<Balance>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAccount(account: Account)

}
