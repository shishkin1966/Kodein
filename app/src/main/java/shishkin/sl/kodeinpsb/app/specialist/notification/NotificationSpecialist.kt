package shishkin.sl.kodeinpsb.app.specialist.notification

import shishkin.sl.kodeinpsb.sl.AbsSpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialist


class NotificationSpecialist : AbsSpecialist(), INotificationSpecialist {
    companion object {
        const val NAME = "NotificationSpecialist"
    }

    private val specialist: INotificationShortSpecialist = NotificationSpecialistFactory.get()

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: ISpecialist): Int {
        return if (other is INotificationSpecialist) 0 else 1
    }

    override fun addNotification(title: String?, message: String) {
        specialist.addNotification(title, message)
    }

    override fun replaceNotification(title: String?, message: String) {
        specialist.replaceNotification(title, message)
    }

    override fun clear() {
        specialist.clear()
    }

    override fun stop() {
        specialist.clear()
    }

}
