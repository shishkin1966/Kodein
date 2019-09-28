package shishkin.sl.kodeinpsb.sl.request

open class Request() : AbsRequest(), IRequest {
    override fun isDistinct(): Boolean {
        return false
    }

    override fun run() {
    }

    override fun getName(): String {
        return this::class.java.simpleName
    }
}
