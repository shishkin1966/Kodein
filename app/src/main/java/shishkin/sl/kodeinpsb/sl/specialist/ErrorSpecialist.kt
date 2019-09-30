package shishkin.sl.kodeinpsb.sl.specialist

import android.Manifest
import android.content.Context
import android.widget.Toast
import com.github.snowdream.android.util.FilePathGenerator
import com.github.snowdream.android.util.Log
import shishkin.sl.kodeinpsb.BuildConfig
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.AbsSpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.data.ExtError
import java.io.File


object ErrorSpecialistSingleton {
    val instance = ErrorSpecialist()
}

class ErrorSpecialist : AbsSpecialist(), IErrorSpecialist {
    companion object {
        const val NAME = "ErrorSpecialist"
    }

    private val MAX_LOG_LENGTH: Long = 2000000//2Mb

    override fun onRegister() {
        checkLog(ApplicationSpecialist.appContext)
    }

    override fun checkLog(context: Context) {
        try {
            Log.setEnabled(true)
            Log.setLog2FileEnabled(true)
            var path: String? = null
            if (BuildConfig.DEBUG && ApplicationUtils.checkPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                path = context.getExternalFilesDir(null)?.absolutePath + File.separator
            }
            if (!path.isNullOrBlank()) {
                val file = File(path)
                if (!file.exists()) {
                    file.mkdirs()
                }
                if (file.exists()) {
                    Log.setFilePathGenerator(
                        FilePathGenerator.DefaultFilePathGenerator(
                            path,
                            "log", ".txt"
                        )
                    )
                    checkLogSize()
                } else {
                    Log.setEnabled(false)
                }
            } else {
                Log.setEnabled(false)
            }
        } catch (e: Exception) {
            Log.setEnabled(false)
        }
    }

    override fun getPath(): String {
        return Log.getPath()
    }

    private fun checkLogSize() {
        val path = Log.getPath()

        try {
            val file = File(path)
            if (file.exists()) {
                if (file.length() == 0L) {
                    Log.i(ApplicationUtils.getPhoneInfo())
                }

                if (file.length() > MAX_LOG_LENGTH) {
                    val newPath = path + ".1"
                    val newFile = File(newPath)
                    if (newFile.exists()) {
                        newFile.delete()
                    }
                    file.renameTo(newFile)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(NAME, e.message)
        }
    }

    override fun onError(source: String, e: Exception) {
        Log.e(source, Log.getStackTraceString(e))
        ApplicationUtils.showToast(
            ApplicationSpecialist.appContext,
            e.message,
            Toast.LENGTH_LONG,
            ApplicationUtils.MESSAGE_TYPE_ERROR
        )
    }

    override fun onError(source: String, throwable: Throwable) {
        Log.e(source, Log.getStackTraceString(throwable))
        ApplicationUtils.showToast(
            ApplicationSpecialist.appContext,
            throwable.message,
            Toast.LENGTH_LONG,
            ApplicationUtils.MESSAGE_TYPE_ERROR
        )
    }

    override fun onError(source: String, e: Exception, displayMessage: String?) {
        Log.e(source, Log.getStackTraceString(e))
        if (!displayMessage.isNullOrEmpty()) {
            ApplicationUtils.showToast(
                ApplicationSpecialist.appContext,
                displayMessage,
                Toast.LENGTH_LONG,
                ApplicationUtils.MESSAGE_TYPE_ERROR
            )
        }
    }

    override fun onError(source: String, message: String?, isDisplay: Boolean) {
        if (!message.isNullOrEmpty()) {
            Log.e(source, message)
            if (isDisplay) {
                ApplicationUtils.showToast(
                    ApplicationSpecialist.appContext,
                    message,
                    Toast.LENGTH_LONG,
                    ApplicationUtils.MESSAGE_TYPE_ERROR
                )
            }
        }
    }

    override fun onError(error: ExtError) {
        if (error.hasError()) {
            ApplicationUtils.showToast(
                ApplicationSpecialist.appContext,
                error.getErrorText(),
                Toast.LENGTH_LONG,
                ApplicationUtils.MESSAGE_TYPE_ERROR
            )
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun isPersistent(): Boolean {
        return true
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is IErrorSpecialist) 0 else 1
    }

}
