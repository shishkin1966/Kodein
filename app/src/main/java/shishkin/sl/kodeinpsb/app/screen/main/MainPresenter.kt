package shishkin.sl.kodeinpsb.app.screen.main

import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.action.HideSideMenuAction
import shishkin.sl.kodeinpsb.app.specialist.UseCasesSpecialist
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.SnackBarAction
import shishkin.sl.kodeinpsb.sl.presenter.AbsModelPresenter
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist

class MainPresenter(model: MainModel) : AbsModelPresenter(model) {
    companion object {
        const val NAME = "MainPresenter"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is SnackBarAction) {
            if (action.getName() == ApplicationSpecialist.appContext.getString(R.string.exit)) {
                ApplicationSingleton.instance.getUseCase().addAction(
                    ApplicationAction(
                        UseCasesSpecialist.OnExit
                    )
                )
            }
            return true
        }
        if (action is HideSideMenuAction) {
            getView<MainActivity>().addAction(action)
            return true
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

}
