package shishkin.sl.kodeinpsb.app.screen.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IErrorSpecialist


class MainActivity : AppCompatActivity() {

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
                if (permition.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ServiceLocatorSingleton.instance.get<IErrorSpecialist>(ErrorSpecialist.NAME)?.checkLog(applicationContext)
                }
            }
        }
    }
}
