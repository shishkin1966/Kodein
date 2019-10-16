package shishkin.sl.kotlin.sl.ui

import shishkin.sl.kotlin.sl.IProviderSubscriber
import shishkin.sl.kotlin.sl.action.IActionHandler
import shishkin.sl.kotlin.sl.action.IActionListener
import shishkin.sl.kotlin.sl.model.IModelView
import shishkin.sl.kotlin.sl.state.IStateable


interface IActivity : IProviderSubscriber, IStateable, IActionListener, IPermissionListener,
    IActionHandler, IModelView {
    /**
     * очистить Back Stack
     */
    fun clearBackStack()

}
