package shishkin.sl.kodeinpsb.sl.action

import microservices.shishkin.sl.ui.MaterialDialogExt
import shishkin.sl.kodeinpsb.R


class ShowDialogAction() : IAction {
    private var _id = -1
    private var _title: String? = null
    private var _message: String? = null
    private var _buttonPositive = R.string.ok_upper
    private var _buttonNegative = MaterialDialogExt.NO_BUTTON
    private var _cancelable = false
    private var _listener: String? = null

    constructor(id: Int, listener: String) : this() {
        _id = id
        _listener = listener
    }

    constructor(id: Int, listener: String, title: String?, message: String) : this(id, listener) {
        _title = title
        _message = message
    }

    fun getMessage(): String? {
        return _message
    }

    fun getTitle(): String? {
        return _title
    }

    fun getButtonPositive(): Int {
        return _buttonPositive
    }

    fun getButtonNegative(): Int {
        return _buttonNegative
    }

    fun isCancelable(): Boolean {
        return _cancelable
    }

    fun getListener(): String? {
        return _listener
    }

    fun setPositiveButton(button: Int): ShowDialogAction {
        _buttonPositive = button
        return this
    }

    fun setNegativeButton(button: Int): ShowDialogAction {
        _buttonNegative = button
        return this
    }

    fun setCancelable(cancelable: Boolean): ShowDialogAction {
        _cancelable = cancelable
        return this
    }

    fun setMessage(message: String): ShowDialogAction {
        _message = message
        return this
    }

    fun setTitle(title: String): ShowDialogAction {
        _title = title
        return this
    }

    fun getId(): Int {
        return _id
    }

}
