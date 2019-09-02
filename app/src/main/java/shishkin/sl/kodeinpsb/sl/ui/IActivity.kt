package shishkin.sl.kodeinpsb.sl.ui

import android.view.View
import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber
import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.state.IStateable
import androidx.annotation.IdRes



interface IActivity : ISpecialistSubscriber, IStateable, IActionListener, IPermissionListener, IActionHandler {

    /**
     * Закрыть
     */
    fun exit()

    /**
     * очистить Back Stack
     */
    fun clearBackStack()

}
