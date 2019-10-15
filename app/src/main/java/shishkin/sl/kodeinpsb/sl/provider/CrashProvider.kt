package shishkin.sl.kodeinpsb.sl.provider

import shishkin.sl.kodeinpsb.sl.IProvider

/**
 * провайдер, протоколирующий Uncaught Exception
 */
class CrashProvider : Thread.UncaughtExceptionHandler, IProvider {
    companion object {
        const val NAME = "CrashProvider"
        private val mHandler = Thread.getDefaultUncaughtExceptionHandler()
    }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        android.util.Log.e(NAME, throwable.message, throwable)
        ErrorSingleton.instance.onError(NAME, throwable)
        mHandler?.uncaughtException(thread, throwable)
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (Thread.UncaughtExceptionHandler::class.java.isInstance(other)) 0 else 1
    }

    override fun isPersistent(): Boolean {
        return true
    }

    override fun onUnRegister() {
    }

    override fun onRegister() {
    }

    override fun stop() {
    }

    override fun getName(): String {
        return NAME
    }

    override fun isValid(): Boolean {
        return true
    }

}

