package shishkin.sl.kodeinpsb.app

import android.widget.Toast
import shishkin.sl.kodeinpsb.app.provider.DbProvider
import shishkin.sl.kodeinpsb.app.specialist.ILocationUnion
import shishkin.sl.kodeinpsb.app.specialist.LocationUnion
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.observe.ScreenObservableSubscriber
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter
import shishkin.sl.kodeinpsb.sl.provider.IDbProvider
import shishkin.sl.kodeinpsb.sl.specialist.*
import shishkin.sl.kodeinpsb.sl.task.CommonExecutor


object ApplicationSingleton {
    val instance = App()
    const val QuitOnExit = "QuitonExit"
}

class App : ApplicationSpecialist() {

    private val screenObservableSubscriber = ScreenObservableSubscriber()

    override fun onCreate() {
        super.onCreate()

        ServiceLocatorSingleton.instance.start()

        serviceLocator = ServiceLocatorSingleton.instance

        ServiceLocatorSingleton.instance.registerSpecialistSubscriber(screenObservableSubscriber)
    }

    fun onScreenOn() {
        ApplicationUtils.showToast(
            appContext,
            "Screen on",
            Toast.LENGTH_SHORT,
            ApplicationUtils.MESSAGE_TYPE_INFO
        )
    }

    fun onScreenOff() {
    }

    fun <C : ISpecialist> get(name: String): C? {
        return serviceLocator?.get(name)
    }

    fun onError(source: String, message: String?, isDisplay: Boolean) {
        val union = serviceLocator?.get<IErrorSpecialist>(ErrorSpecialist.NAME)
        union?.onError(source, message, isDisplay)
    }

    fun onError(source: String, e: Exception) {
        val union = serviceLocator?.get<IErrorSpecialist>(ErrorSpecialist.NAME)
        union?.onError(source, e)
    }

    fun <C : IPresenter> getPresenter(name: String): C? {
        val union = serviceLocator?.get<IPresenterUnion>(PresenterUnion.NAME)
        return union?.getPresenter(name)
    }

    fun addMessage(message: IMessage) {
        val union = serviceLocator?.get<IMessengerUnion>(MessengerUnion.NAME)
        union?.addMessage(message)
    }

    fun addNotMandatoryMessage(message: IMessage) {
        val union = serviceLocator?.get<IMessengerUnion>(MessengerUnion.NAME)
        union?.addNotMandatoryMessage(message)
    }

    fun onChange(observable: String, obj: Any) {
        val union = serviceLocator?.get<IObservableUnion>(ObservableUnion.NAME)
        union?.getObservable(observable)?.onChange(obj)
    }

    fun getDbProvider(): IDbProvider {
        return get(DbProvider.NAME)!!
    }

    fun getObservableUnion(): IObservableUnion {
        return get(ObservableUnion.NAME)!!
    }

    fun getActivityUnion(): IActivityUnion {
        return get(ActivityUnion.NAME)!!
    }

    fun getLocationUnion(): ILocationUnion {
        return get(LocationUnion.NAME)!!
    }

    fun getCache(): ICacheSpecialist {
        return get(CacheSpecialist.NAME)!!
    }

    fun getExecutor(): CommonExecutor {
        return get(CommonExecutor.NAME)!!
    }
}
