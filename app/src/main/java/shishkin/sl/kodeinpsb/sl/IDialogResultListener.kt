package shishkin.sl.kodeinpsb.sl

import shishkin.sl.kodeinpsb.sl.action.DialogResultAction


interface IDialogResultListener : IValidated {
    fun onDialogResult(action: DialogResultAction)
}