package shishkin.sl.kodeinpsb.app.provider.notification


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationConstant
import shishkin.sl.kodeinpsb.app.screen.main.MainActivity
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.provider.ApplicationProvider

class Notification() : INotificationShortProvider {
    private val GROUP_NAME = ApplicationProvider.appContext.getString(R.string.app_name)
    private var id = -1
    private val nm: NotificationManager = ApplicationUtils.getSystemService(
        ApplicationProvider.appContext,
        Context.NOTIFICATION_SERVICE
    )

    override fun addNotification(title: String?, message: String) {
        id = System.currentTimeMillis().toInt()
        show(title, message)
    }

    override fun replaceNotification(title: String?, message: String) {
        if (id == -1) {
            id = System.currentTimeMillis().toInt()
        }
        show(title, message)
    }

    private fun show(title: String?, message: String) {
        val context = ApplicationProvider.appContext
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.action = ApplicationConstant.ACTION_CLICK

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
        )

        val notificationBuilder = NotificationCompat.Builder(context, GROUP_NAME)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.icon)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setDefaults(0)
            .setContentText(message)
        if (!title.isNullOrEmpty()) {
            notificationBuilder.setContentTitle(title)
        }
        nm.notify(id, notificationBuilder.build())
    }

    override fun clear() {
        nm.cancelAll()
    }

    override fun getId(): Int {
        return id
    }

    override fun setId(id: Int) {
        this.id = id
    }
}
