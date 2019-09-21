package shishkin.sl.kodeinpsb.sl.action

import android.widget.Toast
import shishkin.sl.kodeinpsb.common.ApplicationUtils


class ShowMessageAction(private val message: String) : IAction {

    private var _title: String? = null
    private var _duration = Toast.LENGTH_SHORT
    private var _type = ApplicationUtils.MESSAGE_TYPE_INFO

    constructor(message: String, type: Int) : this(message) {
        _type = type
    }

    constructor(title: String, message: String, type: Int) : this(message, type) {
        _title = title
    }

    fun getMessage(): String {
        return message
    }

    fun getTitle(): String? {
        return _title
    }

    fun setTitle(title: String): ShowMessageAction {
        _title = title
        return this
    }

    fun getDuration(): Int {
        return _duration
    }

    fun setDuration(duration: Int): ShowMessageAction {
        _duration = duration
        return this
    }

    fun getType(): Int {
        return _type
    }

    fun setType(type: Int): ShowMessageAction {
        _type = type
        return this
    }
}
