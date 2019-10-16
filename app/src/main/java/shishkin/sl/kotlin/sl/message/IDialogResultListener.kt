package shishkin.sl.kotlin.sl.message

import shishkin.sl.kotlin.sl.IValidated
import shishkin.sl.kotlin.sl.action.DialogResultAction


interface IDialogResultListener : IValidated {
    fun onDialogResult(action: DialogResultAction)
}
