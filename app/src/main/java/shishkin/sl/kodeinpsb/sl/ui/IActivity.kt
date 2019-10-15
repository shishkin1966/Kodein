package shishkin.sl.kodeinpsb.sl.ui

import shishkin.sl.kodeinpsb.sl.IProviderSubscriber
import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IActivity : IProviderSubscriber, IStateable, IActionListener, IPermissionListener,
    IActionHandler, IModelView {
    /**
     * очистить Back Stack
     */
    fun clearBackStack()

}
