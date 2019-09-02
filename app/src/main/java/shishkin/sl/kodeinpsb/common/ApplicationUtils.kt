package shishkin.sl.kodeinpsb.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.annimon.stream.Stream
import com.annimon.stream.function.Predicate
import com.muddzdev.styleabletoast.StyleableToast
import shishkin.sl.kodeinpsb.R

class ApplicationUtils {
    companion object {

        const val REQUEST_PERMISSIONS = 10000
        const val MESSAGE_TYPE_INFO = 0
        const val MESSAGE_TYPE_ERROR = 1
        const val MESSAGE_TYPE_WARNING = 2
        const val MESSAGE_TYPE_SUCCESS = 3

        @JvmStatic
        fun hasHoneycomb(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        }

        @JvmStatic
        fun hasJellyBean(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
        }

        @JvmStatic
        fun hasJellyBeanMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
        }

        @JvmStatic
        fun hasJellyBeanMR2(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
        }

        @JvmStatic
        fun hasKitKat(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        }

        @JvmStatic
        fun isLollipop(): Boolean {
            return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1
        }

        @JvmStatic
        fun hasLollipop(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        @JvmStatic
        fun hasMarshmallow(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        @JvmStatic
        fun hasN(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        }

        @JvmStatic
        fun hasNMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
        }

        @JvmStatic
        fun hasO(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        }

        @JvmStatic
        fun hasOMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1
        }

        @JvmStatic
        fun hasP(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        }

        @JvmStatic
        fun getPhoneInfo(): String {
            val sb = StringBuilder()
            sb.append("\n")
            sb.append("Android version : " + Build.VERSION.RELEASE)
            sb.append("\n")
            sb.append("Board:" + Build.BOARD)
            sb.append("\n")
            sb.append("Manufacturer:" + Build.MANUFACTURER)
            sb.append("\n")
            sb.append("Model:" + Build.MODEL)
            sb.append("\n")
            sb.append("Product:" + Build.PRODUCT)
            sb.append("\n")
            sb.append("Device:" + Build.DEVICE)
            sb.append("\n")
            sb.append("ROM:" + Build.DISPLAY)
            sb.append("\n")
            sb.append("Hardware:" + Build.HARDWARE)
            sb.append("\n")
            sb.append("Id:" + Build.ID)
            sb.append("\n")
            sb.append("Tags:" + Build.TAGS)
            sb.append("\n")
            return sb.toString()
        }

        @JvmStatic
        fun runOnUiThread(action: Runnable) {
            Handler(Looper.getMainLooper()).post(action)
        }

        @JvmStatic
        fun getStatusPermission(context: Context?, permission: String): Int {
            return if (context != null) {
                ActivityCompat.checkSelfPermission(context, permission)
            } else {
                PackageManager.PERMISSION_DENIED
            }
        }

        @JvmStatic
        fun checkPermission(context: Context?, permission: String): Boolean {
            return getStatusPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }

        @JvmStatic
        fun grantPermisions(permissions: Array<String>?, activity: Activity?): Boolean {
            if (activity != null && permissions != null) {
                if (hasMarshmallow()) {
                    val listPermissionsNeeded = ArrayList<String>()

                    for (permission in permissions) {
                        if (ActivityCompat.checkSelfPermission(
                                activity.applicationContext,
                                permission
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            listPermissionsNeeded.add(permission)
                        }
                    }

                    if (!listPermissionsNeeded.isEmpty()) {
                        val arrayPermissionsNeeded =
                            arrayOfNulls<String>(listPermissionsNeeded.size)
                        listPermissionsNeeded.toArray(arrayPermissionsNeeded)
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayPermissionsNeeded,
                            REQUEST_PERMISSIONS
                        )
                        return false
                    }
                } else {
                    return true
                }
            }
            return false
        }

        @JvmStatic
        fun getDrawable(context: Context, id: Int): Drawable? {
            return ResourcesCompat.getDrawable(
                context.resources,
                id,
                if (hasMarshmallow()) context.theme else null
            )
        }

        @JvmStatic
        fun getVectorDrawable(
            context: Context,
            id: Int,
            theme: Resources.Theme
        ): VectorDrawableCompat? {
            return VectorDrawableCompat.create(context.resources, id, theme)
        }

        @JvmStatic
        fun getColor(context: Context, id: Int): Int {
            return ResourcesCompat.getColor(
                context.resources,
                id,
                if (hasMarshmallow()) context.theme else null
            )
        }

        @JvmStatic
        fun getDimensionPx(context: Context, resId: Int): Float {
            return context.resources.getDimension(resId)
        }

        @JvmStatic
        fun getDimensionDp(context: Context, resId: Int): Float {
            return (context.resources.getDimension(resId) / context.resources.displayMetrics.density)
        }

        @JvmStatic
        fun getDimensionSp(context: Context, resId: Int): Float {
            return (context.resources.getDimension(resId) / context.resources.displayMetrics.scaledDensity)
        }

        @JvmStatic
        fun showToast(context: Context, text: String?, duration: Int, type: Int) {
            if (text.isNullOrBlank()) return

            when (type) {
                MESSAGE_TYPE_ERROR -> StyleableToast.Builder(context)
                    .text(text)
                    .textColor(getColor(context, R.color.white))
                    .backgroundColor(getColor(context, R.color.red))
                    .textSize(getDimensionSp(context, R.dimen.textsize_subheader))
                    .cornerRadius(8)
                    .length(duration)
                    .show()
                MESSAGE_TYPE_WARNING -> StyleableToast.Builder(context)
                    .text(text)
                    .textColor(getColor(context, R.color.white))
                    .backgroundColor(getColor(context, R.color.orange))
                    .textSize(getDimensionSp(context, R.dimen.textsize_subheader))
                    .cornerRadius(8)
                    .length(duration)
                    .show()
                else -> StyleableToast.Builder(context)
                    .text(text)
                    .textColor(getColor(context, R.color.white))
                    .backgroundColor(getColor(context, R.color.dark_blue))
                    .textSize(getDimensionSp(context, R.dimen.textsize_subheader))
                    .cornerRadius(8)
                    .length(duration)
                    .show()
            }
        }

        @JvmStatic
        fun showToast(context: Context, resId: Int, duration: Int, type: Int) {
            showToast(context, context.getString(resId), duration, type)
        }

        @JvmStatic
        fun <T> filter(list: Collection<T>, predicate: Predicate<in T>): Stream<T> {
            return Stream.of(list).filter(predicate)
        }

        @JvmStatic
        fun <T> sort(list: Collection<T>, comparator: Comparator<in T>): Stream<T> {
            return Stream.of(list).sorted(comparator)
        }
    }
}
