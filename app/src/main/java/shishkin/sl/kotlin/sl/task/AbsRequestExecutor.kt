package shishkin.sl.kotlin.sl.task

import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.request.IRequest


abstract class AbsRequestExecutor : IRequestExecutor, IProvider {

    abstract fun getExecutor(): RequestThreadPoolExecutor

    override fun execute(request: IRequest) {
        getExecutor().addRequest(request)
    }

    override fun stop() {
        getExecutor().stop()
    }

    override fun clear() {
        getExecutor().clear()
    }

    override fun cancelRequests(listener: String) {
        getExecutor().cancelRequests(listener)
    }

    override fun cancelRequests(listener: String, taskName: String) {
        getExecutor().cancelRequests(listener, taskName)
    }

    override fun isShutdown(): Boolean {
        return getExecutor().isShutdown
    }

    override fun execute(command: Runnable) {
        if (command is IRequest) {
            execute(command)
        }
    }

    override fun onUnRegister() {}

    override fun onRegister() {}

    override fun isValid(): Boolean {
        return !getExecutor().isShutdown
    }

    override fun isPersistent(): Boolean {
        return false
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IExecutor) 0 else 1
    }


}
