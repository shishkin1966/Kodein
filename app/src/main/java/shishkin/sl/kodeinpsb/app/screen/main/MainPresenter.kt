package shishkin.sl.kodeinpsb.app.screen.main

import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.SnackBarAction
import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist

class MainPresenter(model: MainModel) : AbsPresenter(model) {
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
        if (!validate()) return false

        if (action is SnackBarAction) {
            if (action.getName() == ApplicationSpecialist.appContext.getString(R.string.exit)) {
                ApplicationSpecialist.instance.stop()
            }
            return true
        }

        return true
    }

}