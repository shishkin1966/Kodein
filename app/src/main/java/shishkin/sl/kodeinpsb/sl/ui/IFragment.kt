package shishkin.sl.kodeinpsb.sl.ui

import shishkin.sl.kodeinpsb.sl.INamed
import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IFragment : INamed, IModelView, IStateable, IActionListener,
    IPermissionListener,
    IActionHandler {
}
