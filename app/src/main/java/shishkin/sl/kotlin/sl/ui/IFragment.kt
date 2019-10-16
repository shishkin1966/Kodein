package shishkin.sl.kotlin.sl.ui

import shishkin.sl.kotlin.sl.ISubscriber
import shishkin.sl.kotlin.sl.action.IActionHandler
import shishkin.sl.kotlin.sl.action.IActionListener
import shishkin.sl.kotlin.sl.model.IModelView
import shishkin.sl.kotlin.sl.state.IStateable


interface IFragment : ISubscriber, IModelView, IStateable, IActionListener,
    IPermissionListener,
    IActionHandler {
}
