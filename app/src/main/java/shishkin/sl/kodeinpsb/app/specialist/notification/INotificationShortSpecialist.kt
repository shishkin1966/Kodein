package shishkin.sl.kodeinpsb.app.specialist.notification

interface INotificationShortSpecialist {

    fun addNotification(title: String? = null, message: String)

    fun replaceNotification(title: String? = null, message: String)

    fun clear()
}
