package shishkin.sl.kodeinpsb.sl.action.handler

import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.ShowMessageAction
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist


open class BaseActionHandler : IActionHandler {
    override fun onAction(action: IAction): Boolean {
        if (action is ShowMessageAction) {
            showMessage(action)
            return true
        }

        return false
    }

    private fun showMessage(action: ShowMessageAction) {
        if (action.getMessage().isNullOrEmpty()) return

        ApplicationUtils.showToast(
            ApplicationSpecialist.appContext,
            action.getMessage(),
            action.getDuration(),
            action.getType()
        )
    }
}
