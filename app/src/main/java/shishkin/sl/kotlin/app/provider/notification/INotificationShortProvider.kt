package shishkin.sl.kotlin.app.provider.notification

interface INotificationShortProvider {

    fun addNotification(title: String? = null, message: String)

    fun replaceNotification(title: String? = null, message: String)

    fun clear()

    fun getId(): Int

    fun setId(id: Int)
}
