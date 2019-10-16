package shishkin.sl.kotlin.sl.action


class DataAction<T>(name: String, private val data: T?) : ApplicationAction(name) {

    fun getData(): T? {
        return data
    }
}
