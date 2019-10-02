package shishkin.sl.kodeinpsb.sl.message

import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.action.DialogResultAction


interface IDialogResultListener : IValidated {
    fun onDialogResult(action: DialogResultAction)
}
