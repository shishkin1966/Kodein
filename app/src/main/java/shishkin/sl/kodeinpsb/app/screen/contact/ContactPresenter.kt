package shishkin.sl.kodeinpsb.app.screen.contact

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.specialist.UseCasesSpecialist
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.presenter.AbsModelPresenter


class ContactPresenter(model: ContactModel) : AbsModelPresenter(model) {
    companion object {
        const val NAME = "ContactPresenter"

        const val WebAction = "WebAction"
        const val MailAction = "MailAction"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            when (action.getName()) {
                WebAction -> {
                    ApplicationSingleton.instance.getUseCase()
                        .addAction(ApplicationAction(UseCasesSpecialist.ShowProjectWebAction))
                    return true
                }

                MailAction -> {
                    ApplicationSingleton.instance.getUseCase()
                        .addAction(ApplicationAction(UseCasesSpecialist.SendErrorReportAction))
                    return true
                }
            }
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

}
