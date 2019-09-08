package shishkin.sl.kodeinpsb.app.screen.main

import android.Manifest
import android.os.Bundle
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IErrorSpecialist
import shishkin.sl.kodeinpsb.sl.ui.AbsContentActivity


class MainActivity : AbsContentActivity() {

    override fun getName(): String {
        return MainActivity::class.java.simpleName
    }

    override fun onAction(action: IAction): Boolean {
        return true
    }

    override fun createModel(): MainModel {
        return MainModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        if (!ApplicationUtils.checkPermission(
                context = applicationContext,
                permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            ApplicationUtils.grantPermisions(
                permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                activity = this
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == ApplicationUtils.REQUEST_PERMISSIONS) {
            for (permition in permissions) {
                if (permition == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    val union =
                        ServiceLocatorSingleton.instance.get<IErrorSpecialist>(ErrorSpecialist.NAME)
                    union?.checkLog(applicationContext)
                }
            }
        }
    }

}
