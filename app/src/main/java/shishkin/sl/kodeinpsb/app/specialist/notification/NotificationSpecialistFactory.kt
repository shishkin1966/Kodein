package shishkin.sl.kodeinpsb.app.specialist.notification

import shishkin.sl.kodeinpsb.common.ApplicationUtils

object NotificationSpecialistFactory {

    fun get(): INotificationShortSpecialist {
        if (ApplicationUtils.hasO()) {
            return NotificationO()
        } else if (ApplicationUtils.hasMarshmallow()) {
            return NotificationM()
        }
        return Notification()
    }
}
