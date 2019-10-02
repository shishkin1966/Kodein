package shishkin.sl.kodeinpsb.app.specialist

import com.google.common.io.Files
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationConstant
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.setting.Setting
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.AbsSpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.StartActivityAction
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton
import java.io.File

class UseCasesSpecialist : AbsSpecialist(), IUseCasesSpecialist {
    companion object {
        const val NAME = "UseCasesSpecialist"

        const val SendErrorReport = "SendErrorReport"
        const val ShowProjectWeb = "ShowProjectWeb"
        const val OnExit = "OnExit"
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: ISpecialist): Int {
        return if (other is IUseCasesSpecialist) 0 else 1
    }

    override fun addAction(action: IAction) {
        onAction(action)
    }

    private fun onAction(action: IAction): Boolean {
        if (action is ApplicationAction) {
            when (action.getName()) {
                SendErrorReport -> {
                    val rec = ApplicationConstant.MAIL
                    val path = ErrorSpecialistSingleton.instance.getPath()
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
                    if (intent.resolveActivity(ApplicationSpecialist.appContext.packageManager) != null) {
                        ApplicationSingleton.instance.getActivityUnion()
                            .addAction(StartActivityAction(intent))
                    } else {
                        ErrorSpecialistSingleton.instance.onError(
                            getName(), ApplicationSpecialist.appContext.getString(
                                R.string.mail_error
                            ), true
                        )
                    }
                    return true
                }

                ShowProjectWeb -> {
                    ApplicationUtils.showUrl(
                        ApplicationSpecialist.appContext,
                        ApplicationConstant.APP_URL
                    )
                    return true
                }

                OnExit -> {
                    val setting = Setting.restore(ApplicationConstant.QuitOnStopSetting)
                    if (setting == null) {
                        ApplicationSpecialist.instance.toBackground()
                        return true
                    }
                    if (setting.current?.toBoolean()!!) {
                        ApplicationSpecialist.instance.stop()
                    } else {
                        ApplicationSpecialist.instance.toBackground()
                    }
                    return true
                }
            }
        }
        return false
    }


}
