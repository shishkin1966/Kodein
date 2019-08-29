package shishkin.sl.kodeinpsb.common

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.app.Activity
import android.content.Context


class ApplicationUtils {
    companion object {

        const val REQUEST_PERMISSIONS = 10000

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
        fun getPhoneInfo() : String {
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
                ActivityCompat.checkSelfPermission(context!!, permission)
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
                        listPermissionsNeeded.toTypedArray()
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

    }
}