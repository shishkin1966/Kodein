package shishkin.sl.kodeinpsb.sl.ui

import shishkin.sl.kodeinpsb.sl.ISubscriber
import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IFragment<M : IModel> : ISubscriber, IModelView, IStateable, IActionListener,
    IPermissionListener,
    IActionHandler {
}
