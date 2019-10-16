package shishkin.sl.kotlin.app.provider

import com.google.common.io.Files
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationConstant
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.setting.Setting
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.sl.AbsShortLiveProvider
import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.action.ApplicationAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.StartActivityAction
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.provider.ErrorSingleton
import java.io.File

class UseCasesProvider : AbsShortLiveProvider(),
    IUseCasesProvider {
    companion object {
        const val NAME = "UseCasesProvider"

        const val SendErrorReportAction = "SendErrorReportAction"
        const val ShowProjectWebAction = "ShowProjectWebAction"
        const val OnExitAction = "OnExitAction"
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is IUseCasesProvider) 0 else 1
    }

    override fun addAction(action: IAction) {
        onAction(action)
    }

    private fun onAction(action: IAction): Boolean {
        post()
        if (action is ApplicationAction) {
            when (action.getName()) {
                SendErrorReportAction -> {
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
                        ApplicationSingleton.instance.onError(getName(), e)
                    }

                    val intent = ApplicationUtils.getEmailIntent(rec, "Ошибка", body)
                    if (intent.resolveActivity(ApplicationProvider.appContext.packageManager) != null) {
                        ApplicationSingleton.instance.getActivityUnion()
                            .addAction(StartActivityAction(intent))
                    } else {
                        ErrorSingleton.instance.onError(
                            getName(), ApplicationProvider.appContext.getString(
                                R.string.mail_error
                            ), true
                        )
                    }
                    return true
                }

                ShowProjectWebAction -> {
                    ApplicationUtils.showUrl(
                        ApplicationProvider.appContext,
                        ApplicationConstant.APP_URL
                    )
                    return true
                }

                OnExitAction -> {
                    val setting = Setting.restore(ApplicationConstant.QuitOnStopSetting)
                    if (setting == null) {
                        ApplicationProvider.instance.toBackground()
                        return true
                    }
                    if (setting.current?.toBoolean()!!) {
                        ApplicationProvider.instance.stop()
                    } else {
                        ApplicationProvider.instance.toBackground()
                    }
                    return true
                }
            }
        }
        return false
    }


}
