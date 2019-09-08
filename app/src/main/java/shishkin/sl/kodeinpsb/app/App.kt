package shishkin.sl.kodeinpsb.app

import android.widget.Toast
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.observe.ScreenObservableSubscriber
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist


object ApplicationSingleton {
    val instance = App()
}

class App : ApplicationSpecialist() {

    private val screenObservableSubscriber = ScreenObservableSubscriber()

    override fun onCreate() {
        super.onCreate()

        ServiceLocatorSingleton.instance.start()

        ServiceLocatorSingleton.instance.registerSpecialistSubscriber(screenObservableSubscriber)
    }

    override fun exit() {
        super.exit()

        ServiceLocatorSingleton.instance.stop()
    }

    fun onScreenOn() {
        ApplicationUtils.showToast(
            ApplicationSpecialist.appContext,
            "Screen on",
            Toast.LENGTH_SHORT,
            ApplicationUtils.MESSAGE_TYPE_INFO
        )
    }

    fun onScreenOff() {
    }
}
