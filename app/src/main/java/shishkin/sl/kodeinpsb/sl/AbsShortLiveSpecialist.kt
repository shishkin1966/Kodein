package shishkin.sl.kodeinpsb.sl

import android.widget.Toast
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.common.AutoCompleteHandler
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import java.util.concurrent.TimeUnit


abstract class AbsShortLiveSpecialist : AbsSpecialist(), AutoCompleteHandler.OnShutdownListener {
    private val serviceHandler: AutoCompleteHandler<Boolean> =
        AutoCompleteHandler("AbsShortLiveSpecialist [" + getName() + "]")
    private val TIMEUNIT = TimeUnit.SECONDS
    private val TIMEUNIT_DURATION = 10L

    init {
        serviceHandler.setOnShutdownListener(this)
        serviceHandler.post(true)
        setShutdownTimeout(TIMEUNIT.toMillis(TIMEUNIT_DURATION))
    }

    fun setShutdownTimeout(shutdownTimeout: Long) {
        if (shutdownTimeout > 0) {
            serviceHandler.setShutdownTimeout(shutdownTimeout)
        }
    }

    fun post() {
        serviceHandler.post(true)
    }

    override fun onShutdown(handler: AutoCompleteHandler<*>) {
        ApplicationSpecialist.serviceLocator?.unregisterSpecialist(getName())
    }

}
