package shishkin.sl.kodeinpsb.sl.provider

import android.Manifest
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.common.io.Files
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.Secretary
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton
import java.io.File


abstract class AbsDbProvider : IDbProvider {
    private val databases = Secretary<RoomDatabase>()

    private val callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            onCreateDatabase(db)
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            onOpenDatabase(db)
        }
    }

    override fun onCreateDatabase(db: SupportSQLiteDatabase) {}

    override fun onOpenDatabase(db: SupportSQLiteDatabase) {}

    private fun <T : RoomDatabase> connect(
        klass: Class<T>,
        databaseName: String,
        migration: Migration?
    ): Boolean {
        val context = ApplicationSpecialist.appContext

        try {
            val db: T
            if (migration == null) {
                db = Room.databaseBuilder(context, klass, databaseName)
                    .addCallback(callback)
                    .build()
            } else {
                db = Room.databaseBuilder(context, klass, databaseName)
                    .addMigrations(migration)
                    .addCallback(callback)
                    .build()
            }
            db.openHelper.readableDatabase.version
            databases.put(databaseName, db);

        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(getName(), e)
        }
        return isConnected(databaseName)
    }

    private fun isConnected(databaseName: String): Boolean {
        return if (databaseName.isNullOrEmpty()) {
            false
        } else databases.containsKey(databaseName)

    }

    private fun disconnect(databaseName: String): Boolean {
        if (isConnected(databaseName)) {
            val db = databases.get(databaseName)
            try {
                db?.close()
                databases.remove(databaseName)
            } catch (e: Exception) {
                ErrorSpecialistSingleton.instance.onError(getName(), e)
            }
        }
        return !isConnected(databaseName)
    }

    override fun <T : RoomDatabase> getDb(klass: Class<T>, databaseName: String): T {
        if (!isConnected(databaseName)) {
            connect(klass, databaseName, null)
        }
        return databases.get(databaseName) as T
    }

    override fun <T : RoomDatabase> getDb(
        klass: Class<T>,
        databaseName: String,
        migration: Migration
    ): T {
        if (!isConnected(databaseName)) {
            connect(klass, databaseName, migration)
        }
        return databases.get(databaseName) as T
    }

    override fun <T : RoomDatabase> getDb(databaseName: String): T? {
        return if (databases.containsKey(databaseName)) {
            databases.get(databaseName) as T
        } else null
    }

    override fun <T : RoomDatabase> getDb(): T? {
        return if (!databases.isEmpty()) {
            databases.values().iterator().next() as T
        } else null
    }

    override fun isPersistent(): Boolean {
        return false
    }

    override fun onUnRegister() {
        stop()
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun stop() {
        for (databaseName in databases.keys()) {
            disconnect(databaseName)
        }
    }

    override fun compareTo(other: ISpecialist): Int {
        return if (other is IDbProvider) 0 else 1
    }

    override fun <T : RoomDatabase> backup(
        databaseName: String,
        dirBackup: String,
        klass: Class<T>?
    ) {
        val context = ApplicationSpecialist.appContext
        if (context == null) return

        if (!ApplicationUtils.checkPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            return
        }

        val db = databases.get(databaseName) ?: return

        val pathDb = db.getOpenHelper().getReadableDatabase().getPath()
        if (pathDb.isNullOrEmpty()) {
            return
        }

        disconnect(databaseName)

        val fileDb = File(pathDb)
        val nameDb = fileDb.getName()
        val pathBackup = dirBackup + File.separator + nameDb
        try {
            val fileBackup = File(pathBackup)
            val fileBackupOld = File(pathBackup + "1")
            if (fileDb.exists()) {
                if (fileBackup.exists()) {
                    val dir = File(dirBackup)
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    if (dir.exists()) {
                        if (fileBackupOld.exists()) {
                            fileBackupOld.delete()
                        }
                        if (!fileBackupOld.exists()) {
                            Files.copy(fileBackup, fileBackupOld)
                            if (fileBackupOld.exists()) {
                                fileBackup.delete()
                                if (!fileBackup.exists()) {
                                    Files.copy(fileDb, fileBackup)
                                    if (fileBackup.exists()) {
                                        fileBackupOld.delete()
                                    } else {
                                        Files.copy(fileBackupOld, fileBackup)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    val dir = File(dirBackup)
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    if (dir.exists()) {
                        Files.copy(fileDb, fileBackup)
                    }
                }
            }

            if (klass != null) {
                connect(klass, nameDb, null);
            }

            ApplicationUtils.runOnUiThread(Runnable {
                ApplicationUtils.showToast(
                    context,
                    context.getString(R.string.db_backuped),
                    Toast.LENGTH_LONG,
                    ApplicationUtils.MESSAGE_TYPE_INFO
                )

            })
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(getName(), e)
        }
    }


    override fun <T : RoomDatabase> restore(
        databaseName: String,
        dirBackup: String,
        klass: Class<T>?
    ) {
        val context = ApplicationSpecialist.appContext
        if (context == null) return

        if (!ApplicationUtils.checkPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            return
        }

        val db = databases.get(databaseName) ?: return

        val pathDb = db.getOpenHelper().getReadableDatabase().getPath()
        if (pathDb.isNullOrEmpty()) {
            return
        }

        disconnect(databaseName)

        val fileDb = File(pathDb)
        val nameDb = fileDb.getName()
        val pathBackup = dirBackup + File.separator + nameDb
        val fileBackup = File(pathBackup)
        if (fileBackup.exists()) {
            try {
                if (fileDb.exists()) {
                    fileDb.delete()
                }
                if (!fileDb.exists()) {
                    Files.createParentDirs(fileDb)
                    val dir = File(fileDb.getParent())
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    if (dir.exists()) {
                        Files.copy(fileBackup, fileDb)
                    }
                }

                if (klass != null) {
                    connect(klass, nameDb, null);
                }

                ApplicationUtils.runOnUiThread(
                    Runnable
                    {
                        ApplicationUtils.showToast(
                            context,
                            context.getString(R.string.db_restored),
                            Toast.LENGTH_LONG,
                            ApplicationUtils.MESSAGE_TYPE_INFO
                        )
                    })
            } catch (e: Exception) {
                ErrorSpecialistSingleton.instance.onError(getName(), e)
            }
        }
    }

    override fun checkCopy(databaseName: String, dirBackup: String): Boolean {
        val context = ApplicationSpecialist.appContext
        if (context == null) return false

        if (!ApplicationUtils.checkPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            return false
        }

        val db = databases.get(databaseName) ?: return false

        val pathDb = db.getOpenHelper().getReadableDatabase().getPath()
        if (pathDb.isNullOrEmpty()) {
            return false
        }

        val fileDb = File(pathDb)
        val nameDb = fileDb.getName()
        val pathBackup = dirBackup + nameDb

        return File(pathBackup).exists()
    }
}
