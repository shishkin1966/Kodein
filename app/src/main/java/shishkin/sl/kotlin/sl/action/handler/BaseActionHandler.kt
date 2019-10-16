package shishkin.sl.kotlin.sl.action.handler

import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.IActionHandler
import shishkin.sl.kotlin.sl.action.ShowMessageAction
import shishkin.sl.kotlin.sl.provider.ApplicationProvider


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
            ApplicationProvider.appContext,
            action.getMessage(),
            action.getDuration(),
            action.getType()
        )
    }
}
