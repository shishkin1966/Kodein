package shishkin.sl.kotlin.app.provider.notification

import shishkin.sl.kotlin.common.PreferencesUtils
import shishkin.sl.kotlin.sl.AbsShortLiveProvider
import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.provider.ApplicationProvider


class NotificationProvider : AbsShortLiveProvider(), INotificationProvider {
    companion object {
        const val NAME = "NotificationProvider"
    }

    private val provider: INotificationShortProvider = NotificationProviderFactory.get()

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is INotificationProvider) 0 else 1
    }

    override fun addNotification(title: String?, message: String) {
        post()
        provider.addNotification(title, message)
    }

    override fun replaceNotification(title: String?, message: String) {
        post()
        provider.replaceNotification(title, message)
    }

    override fun clear() {
        post()
        provider.clear()
    }

    override fun stop() {
        provider.clear()
    }

    override fun onUnRegister() {
        PreferencesUtils.putInt(ApplicationProvider.appContext, NAME, getId())

        super.onUnRegister()
    }

    override fun onRegister() {
        super.onRegister()

        setId(PreferencesUtils.getInt(ApplicationProvider.appContext, NAME))
    }

    override fun getId(): Int {
        return provider.getId()
    }

    override fun setId(id: Int) {
        provider.setId(id)
    }


}
