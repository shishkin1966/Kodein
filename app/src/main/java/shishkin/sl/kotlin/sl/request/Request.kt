package shishkin.sl.kotlin.sl.request

open class Request() : AbsRequest() {
    override fun isDistinct(): Boolean {
        return false
    }

    override fun run() {
    }

    override fun getName(): String {
        return this::class.java.simpleName
    }
}
