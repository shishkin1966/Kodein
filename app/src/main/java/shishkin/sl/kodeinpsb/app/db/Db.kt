package shishkin.sl.kodeinpsb.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import shishkin.sl.kodeinpsb.BuildConfig
import shishkin.sl.kodeinpsb.app.data.Account
import shishkin.sl.kodeinpsb.app.db.Db.Companion.VERSION


@Database(entities = [Account::class], version = VERSION, exportSchema = false)
abstract class Db : RoomDatabase() {
    companion object {
        const val NAME = BuildConfig.APPLICATION_ID + ".db"
        const val VERSION = 1
    }

    abstract fun getDao(): Dao
}