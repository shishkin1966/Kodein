package shishkin.sl.kodeinpsb.app.screen.main

import android.Manifest
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.observe.NetObservable
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber
import shishkin.sl.kodeinpsb.sl.specialist.ObservableUnion
import shishkin.sl.kodeinpsb.sl.ui.AbsActivity


class MainActivity : AbsActivity(), IObservableSubscriber {

    private var snackbar: Snackbar? = null

    override fun getName(): String {
        return MainActivity::class.java.simpleName
    }

    override fun onAction(action: IAction): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun getObservable(): List<String> {
        return listOf(NetObservable.NAME)
    }

    override fun onChange(name: String, obj: Any) {
        if (name == NetObservable.NAME) {
            if (obj == true) {
                onConnected()
            } else if (obj == false) {
                onDisConnected()
            }
        }
    }

    private fun onConnected() {
        if (snackbar?.isShown == true) {
            snackbar?.dismiss()
            snackbar = null
        }
    }

    private fun onDisConnected() {
        if (snackbar != null) {
            snackbar?.dismiss()
            snackbar = null
        }
        snackbar = ApplicationUtils.showSnackbar(
            getRootView(),
            getString(R.string.network_disconnected),
            Snackbar.LENGTH_INDEFINITE,
            ApplicationUtils.MESSAGE_TYPE_WARNING
        )
    }

    override fun getSpecialistSubscription(): List<String> {
        val list = ArrayList<String>()
        list.addAll(super.getSpecialistSubscription())
        list.add(ObservableUnion.NAME)
        return list
    }

}
