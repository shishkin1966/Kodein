package shishkin.sl.kodeinpsb.sl.action

import com.google.android.material.snackbar.Snackbar
import shishkin.sl.kodeinpsb.common.ApplicationUtils


class ShowSnackbarAction(private val message: String) : IAction {

    private var _action: String? = null
    private var _duration = Snackbar.LENGTH_SHORT
    private var _type = ApplicationUtils.MESSAGE_TYPE_INFO

    fun getMessage(): String {
        return message
    }

    fun getAction(): String? {
        return _action
    }

    fun setAction(action: String): ShowSnackbarAction {
        _action = action
        return this
    }

    fun getDuration(): Int {
        return _duration
    }

    fun setDuration(duration: Int): ShowSnackbarAction {
        _duration = duration
        return this
    }

    fun getType(): Int {
        return _type
    }

    fun setType(type: Int): ShowSnackbarAction {
        _type = type
        return this
    }

}
