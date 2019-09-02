package shishkin.sl.kodeinpsb.app

import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist

object ApplicationSingleton {
    val instance = App()
}

class App : ApplicationSpecialist() {
    override fun onCreate() {
        super.onCreate()

        ServiceLocatorSingleton.instance.start()
    }

    override fun exit() {
        super.exit()

        ServiceLocatorSingleton.instance.stop()
    }
}
