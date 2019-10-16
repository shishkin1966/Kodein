package shishkin.sl.kotlin.app

import android.widget.Toast
import shishkin.sl.kotlin.app.provider.*
import shishkin.sl.kotlin.app.provider.notification.INotificationProvider
import shishkin.sl.kotlin.app.provider.notification.NotificationProvider
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.IProviderSubscriber
import shishkin.sl.kotlin.sl.IRouter
import shishkin.sl.kotlin.sl.message.IMessage
import shishkin.sl.kotlin.sl.observe.ScreenObservableSubscriber
import shishkin.sl.kotlin.sl.presenter.IModelPresenter
import shishkin.sl.kotlin.sl.provider.*
import shishkin.sl.kotlin.sl.request.IRequest
import shishkin.sl.kotlin.sl.task.CommonExecutor
import shishkin.sl.kotlin.sl.ui.IActivity


object ApplicationSingleton {
    val instance = App()
}

class App : ApplicationProvider() {

    private val screenObservableSubscriber = ScreenObservableSubscriber()

    override fun onCreate() {
        super.onCreate()

        ServiceLocatorSingleton.instance.start()

        serviceLocator = ServiceLocatorSingleton.instance

        ServiceLocatorSingleton.instance.registerSubscriber(screenObservableSubscriber)
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

    fun <C : IProvider> get(name: String): C? {
        return serviceLocator?.get(name)
    }

    fun onError(source: String, message: String?, isDisplay: Boolean) {
        val union = serviceLocator?.get<IErrorProvider>(ErrorProvider.NAME)
        union?.onError(source, message, isDisplay)
    }

    fun onError(source: String, e: Exception) {
        val union = serviceLocator?.get<IErrorProvider>(ErrorProvider.NAME)
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

    fun getCache(): ICacheProvider {
        return get(CacheProvider.NAME)!!
    }

    fun cancelRequests(name: String) {
        get<CommonExecutor>(CommonExecutor.NAME)!!.cancelRequests(name)
    }

    fun execute(request: IRequest) {
        get<CommonExecutor>(CommonExecutor.NAME)!!.execute(request)
    }

    fun getUseCase(): IUseCasesProvider {
        return get(UseCasesProvider.NAME)!!
    }

    fun getNotification(): INotificationProvider {
        return get(NotificationProvider.NAME)!!
    }

    fun getRouter(): IRouter {
        return getActivityUnion().getActivity<IActivity>() as IRouter
    }

    fun addNotMandatoryMessage(message: IMessage) {
        get<IMessengerUnion>(MessengerUnion.NAME)!!.addNotMandatoryMessage(message)
    }

    fun registerSubscriber(subscriber: IProviderSubscriber): Boolean {
        return serviceLocator!!.registerSubscriber(subscriber)
    }

    fun unregisterSubscriber(subscriber: IProviderSubscriber): Boolean {
        return serviceLocator!!.unregisterSubscriber(subscriber)
    }
}
