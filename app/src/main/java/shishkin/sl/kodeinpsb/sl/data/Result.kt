package shishkin.sl.kodeinpsb.sl.data


class Result<T> {

    val NOT_SEND = -1
    val LAST = -2

    private var data: T? = null
    private var error: Error? = null
    private var order = NOT_SEND
    private var name: String? = null
    private var id = 0

    constructor(data: T?) {
        this.data = data
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T?): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        this.data = data
        return this;
    }

    fun getError(): Error? {
        return error
    }

    fun setError(error: Error): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        this.error = error;
        return this;
    }

    fun setError(sender: String, error: String): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        if (error == null) {
            this.error = Error()
        }
        this.error!!.addError(sender, error)
        return this
    }

    fun setError(sender: String, e: Exception): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        if (error == null) {
            error = Error()
        }
        error!!.addError(sender, e)
        return this
    }

    fun setError(sender: String, t: Throwable): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        if (error == null) {
            error = Error()
        }
        error!!.addError(sender, t.message)
        return this
    }

    fun getErrorText(): String? {
        return if (error != null) {
            error!!.getErrorText()
        } else null
    }

    fun getSender(): String? {
        return if (error != null) {
            error!!.getSender()
        } else null
    }

    fun validate(): Boolean {
        return if (error == null) {
            true
        } else !error!!.hasError()
    }

    fun isEmpty(): Boolean {
        return data == null
    }

    fun getOrder(): Int {
        return order
    }

    fun setOrder(order: Int): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        this.order = order
        return this
    }

    fun hasError(): Boolean {
        return if (error != null) {
            error!!.hasError()
        } else false
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        this.name = name
        return this
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int): shishkin.sl.kodeinpsb.sl.data.Result<T> {
        this.id = id
        return this
    }
}