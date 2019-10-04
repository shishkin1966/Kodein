package shishkin.sl.kodeinpsb.app

import android.widget.Toast
import shishkin.sl.kodeinpsb.app.provider.DbProvider
import shishkin.sl.kodeinpsb.app.specialist.ILocationUnion
import shishkin.sl.kodeinpsb.app.specialist.IUseCasesSpecialist
import shishkin.sl.kodeinpsb.app.specialist.LocationUnion
import shishkin.sl.kodeinpsb.app.specialist.UseCasesSpecialist
import shishkin.sl.kodeinpsb.app.specialist.notification.INotificationSpecialist
import shishkin.sl.kodeinpsb.app.specialist.notification.NotificationSpecialist
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.IRouter
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.observe.ScreenObservableSubscriber
import shishkin.sl.kodeinpsb.sl.presenter.IModelPresenter
import shishkin.sl.kodeinpsb.sl.provider.IDbProvider
import shishkin.sl.kodeinpsb.sl.request.IRequest
import shishkin.sl.kodeinpsb.sl.specialist.*
import shishkin.sl.kodeinpsb.sl.task.CommonExecutor
import shishkin.sl.kodeinpsb.sl.ui.IActivity


object ApplicationSingleton {
    val instance = App()
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

    fun <C : IModelPresenter> getPresenter(name: String): C? {
        val union = serviceLocator?.get<IPresenterUnion>(PresenterUnion.NAME)
        return union?.getPresenter(name)
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

    fun execute(request: IRequest) {
        get<CommonExecutor>(CommonExecutor.NAME)!!.execute(request)
    }

    fun getUseCase(): IUseCasesSpecialist {
        return get(UseCasesSpecialist.NAME)!!
    }

    fun getNotification(): INotificationSpecialist {
        return get(NotificationSpecialist.NAME)!!
    }

    fun getRouter(): IRouter {
        return getActivityUnion().getActivity<IActivity>() as IRouter
    }
}
