package shishkin.sl.kodeinpsb.app.provider.notification

import shishkin.sl.kodeinpsb.common.ApplicationUtils

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
