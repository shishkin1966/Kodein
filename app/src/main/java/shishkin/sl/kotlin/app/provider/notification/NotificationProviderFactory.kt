package shishkin.sl.kotlin.app.provider.notification

import shishkin.sl.kotlin.common.ApplicationUtils

object NotificationProviderFactory {

    fun get(): INotificationShortProvider {
        if (ApplicationUtils.hasO()) {
            return NotificationO()
        } else if (ApplicationUtils.hasMarshmallow()) {
            return NotificationM()
        }
        return Notification()
    }
}
