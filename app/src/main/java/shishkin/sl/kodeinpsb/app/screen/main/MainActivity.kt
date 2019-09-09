package shishkin.sl.kodeinpsb.app.screen.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.ActivityActionHandler
import shishkin.sl.kodeinpsb.sl.presenter.OnBackPressedPresenter
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IErrorSpecialist
import shishkin.sl.kodeinpsb.sl.ui.AbsContentActivity

class MainActivity : AbsContentActivity() {

    private val onBackPressedPresenter = OnBackPressedPresenter()
    private val actionHandler = ActivityActionHandler(this)

    override fun getName(): String {
        return MainActivity::class.java.simpleName
    }

    override fun onAction(action: IAction): Boolean {
        return actionHandler.onAction(action)
    }

    override fun createModel(): MainModel {
        return MainModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        onNewIntent(getIntent())
    }

    override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }

    override fun onStart() {
        super.onStart()

        ApplicationUtils.grantPermisions(
            permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            activity = this
        )

        addStateObserver(onBackPressedPresenter)

        if (intent != null) {
            val action = intent.getAction();
            if ("android.intent.action.MAIN" == action) {
                showHomeFragment();
            } else {
                showHomeFragment();
            }
            intent = null;
        } else {
            showHomeFragment();
        }
    }

    fun showHomeFragment() {
        clearBackStack()
        //showFragment(AccountsFragment.newInstance(), true)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == ApplicationUtils.REQUEST_PERMISSIONS) {
            for (permission in permissions) {
                if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    val union =
                        ServiceLocatorSingleton.instance.get<IErrorSpecialist>(ErrorSpecialist.NAME)
                    union?.checkLog(applicationContext)
                }
            }
        }
    }

    override fun onBackPressed() {
        onBackPressedPresenter.onClick()
    }

}
