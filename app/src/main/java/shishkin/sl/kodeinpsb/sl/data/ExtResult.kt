package shishkin.sl.kodeinpsb.sl.data

class ExtResult() {
    companion object {
        const val NOT_SEND = -1
        const val LAST = -2
    }

    private var _data: Any? = null
    private var _error: ExtError? = null
    private var _order = NOT_SEND
    private var _name: String? = null
    private var _id = 0

    constructor(data: Any?) : this() {
        _data = data
    }

    fun getData(): Any? {
        return _data
    }

    fun setData(data: Any?): ExtResult {
        _data = data
        return this;
    }

    fun getError(): ExtError? {
        return _error
    }

    fun setError(error: ExtError?): ExtResult {
        _error = error;
        return this;
    }

    fun setError(sender: String, error: String): ExtResult {
        if (_error == null) {
            _error = ExtError()
        }
        _error?.addError(sender, error)
        return this
    }

    fun setError(sender: String, e: Exception): ExtResult {
        if (_error == null) {
            _error = ExtError()
        }
        _error?.addError(sender, e)
        return this
    }

    fun setError(sender: String, t: Throwable): ExtResult {
        if (_error == null) {
            _error = ExtError()
        }
        _error?.addError(sender, t.message)
        return this
    }

    fun getErrorText(): String? {
        return if (_error != null) {
            _error?.getErrorText()
        } else null
    }

    fun getSender(): String? {
        return if (_error != null) {
            _error?.getSender()
        } else null
    }

    fun validate(): Boolean {
        return if (_error == null) {
            true
        } else !_error!!.hasError()
    }

    fun isEmpty(): Boolean {
        return _data == null
    }

    fun getOrder(): Int {
        return _order
    }

    fun setOrder(order: Int): ExtResult {
        _order = order
        return this
    }

    fun hasError(): Boolean {
        return if (_error != null) {
            _error!!.hasError()
        } else false
    }

    fun getName(): String? {
        return _name
    }

    fun setName(name: String): ExtResult {
        _name = name
        return this
    }

    fun getId(): Int {
        return _id
    }

    fun setId(id: Int): ExtResult {
        _id = id
        return this
    }
}
