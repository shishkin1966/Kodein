package shishkin.sl.kotlin.app.provider

import com.google.common.io.Files
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationConstant
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.app.db.Db
import shishkin.sl.kotlin.app.request.*
import shishkin.sl.kotlin.app.setting.Setting
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.common.getDir
import shishkin.sl.kotlin.sl.action.StartActivityAction
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.provider.ErrorSingleton
import java.io.File

class Providers {
    companion object {
        @JvmStatic
        fun getAccounts(subscriber: String) {
            ApplicationSingleton.instance.dbProvider.request(GetAccountsRequest(subscriber))
        }

        @JvmStatic
        fun getBalance(subscriber: String) {
            ApplicationSingleton.instance.dbProvider.request(GetBalanceRequest(subscriber))
        }

        @JvmStatic
        fun createAccount(account: Account) {
            ApplicationSingleton.instance.dbProvider.request(CreateAccountRequest(account))
        }

        @JvmStatic
        fun getCurrency(subscriber: String) {
            ApplicationSingleton.instance.dbProvider.request(GetCurrencyRequest(subscriber))
        }

        @JvmStatic
        fun getTickers(subscriber: String) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetTickersRequest(subscriber))
        }

        @JvmStatic
        fun checkDbCopy(): Boolean {
            return ApplicationSingleton.instance.dbProvider
                .checkCopy(Db.NAME, ErrorSingleton.instance.getPath().getDir())
        }

        @JvmStatic
        fun backupDb() {
            return ApplicationSingleton.instance.dbProvider.backup(
                Db.NAME,
                ErrorSingleton.instance.getPath().getDir(),
                Db::class.java
            )
        }

        @JvmStatic
        fun restoreDb() {
            return ApplicationSingleton.instance.dbProvider.restore(
                Db.NAME,
                ErrorSingleton.instance.getPath().getDir(),
                Db::class.java
            )
        }

        @JvmStatic
        fun sendErrorReport() {
            val rec = ApplicationConstant.MAIL
            val path = ErrorSingleton.instance.getPath()
            var body = "\n" + ApplicationUtils.getPhoneInfo()
            try {
                val file = File(path)
                if (file.exists()) {
                    val log = Files.toString(file, Charsets.UTF_8)
                    body = "\n" + log
                }
            } catch (e: Exception) {
                ApplicationSingleton.instance.onError("sendErrorReport", e)
            }

            val intent = ApplicationUtils.getEmailIntent(rec, "Ошибка", body)
            if (intent.resolveActivity(ApplicationProvider.appContext.packageManager) != null) {
                ApplicationSingleton.instance.activityProvider
                    .addAction(StartActivityAction(intent))
            } else {
                ErrorSingleton.instance.onError(
                    "sendErrorReport", ApplicationProvider.appContext.getString(
                        R.string.mail_error
                    ), true
                )
            }

        }

        @JvmStatic
        fun showProjectWeb() {
            ApplicationUtils.showUrl(
                ApplicationProvider.appContext,
                ApplicationConstant.APP_URL
            )
        }

        @JvmStatic
        fun exitApplication() {
            val setting = Setting.restore(ApplicationConstant.QuitOnStopSetting)
            if (setting == null) {
                ApplicationProvider.instance.toBackground()
                return
            }
            if (setting.current?.toBoolean()!!) {
                ApplicationProvider.instance.stop()
            } else {
                ApplicationProvider.instance.toBackground()
            }
        }
    }
}
