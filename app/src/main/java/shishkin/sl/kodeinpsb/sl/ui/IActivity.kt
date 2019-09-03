package shishkin.sl.kodeinpsb.sl.ui

import android.view.View
import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber
import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.state.IStateable
import androidx.annotation.IdRes
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.model.IModelView


interface IActivity : ISpecialistSubscriber, IStateable, IActionListener, IPermissionListener, IActionHandler,
    IModelView {
    /**
     * очистить Back Stack
     */
    fun clearBackStack()

}
