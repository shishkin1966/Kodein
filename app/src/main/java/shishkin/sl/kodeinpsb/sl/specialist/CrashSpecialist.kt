package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISpecialist

/**
 * Специалист, протоколирующий Uncaught Exception
 */
class CrashSpecialist : Thread.UncaughtExceptionHandler, ISpecialist {
    companion object {
        const val NAME = "CrashSpecialist"
        private val mHandler = Thread.getDefaultUncaughtExceptionHandler()
    }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        android.util.Log.e(NAME, throwable.message, throwable)
        ErrorSpecialistSingleton.instance.onError(NAME, throwable)
        mHandler?.uncaughtException(thread, throwable)
    }

    override operator fun compareTo(other: ISpecialist): Int {
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

