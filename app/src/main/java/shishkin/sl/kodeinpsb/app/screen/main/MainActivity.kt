package shishkin.sl.kodeinpsb.app.screen.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.app.screen.accounts.AccountsFragment
import shishkin.sl.kodeinpsb.app.screen.sidemenu.SideMenuFragment
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.common.SlidingMenu
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.ActivityActionHandler
import shishkin.sl.kodeinpsb.sl.presenter.OnBackPressedPresenter
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IErrorSpecialist
import shishkin.sl.kodeinpsb.sl.ui.AbsContentActivity
import shishkin.sl.kodeinpsb.sl.ui.BackStack

class MainActivity : AbsContentActivity() {

    private val onBackPressedPresenter = OnBackPressedPresenter()
    private val actionHandler = ActivityActionHandler(this)
    private var menu: SlidingMenu? = null

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (actionHandler.onAction(action)) return true

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:" + action.toString(),
            true
        );
        return false
    }

    override fun createModel(): MainModel {
        return MainModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setMenu();

        onNewIntent(getIntent())
    }

    override fun getContentResId(): Int {
        return R.id.content
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
        showFragment(AccountsFragment.newInstance(), true)
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

    private fun setMenu() {
        if (menu == null) {
            menu = SlidingMenu(this)
            menu?.setMode(SlidingMenu.LEFT)
            menu?.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN)
            menu?.setShadowWidthRes(R.dimen.dimen_8dp)
            menu?.setBehindOffsetRes(R.dimen.slidingmenu_offset)
            menu?.setShadowDrawable(R.drawable.shadow)
            menu?.setFadeDegree(0.35f)
            menu?.attachToActivity(this, SlidingMenu.SLIDING_CONTENT)
            menu?.setMenu(R.layout.menu_container)
        }

        BackStack.showFragment(
            this,
            R.id.menu,
            SideMenuFragment.newInstance(),
            false,
            false,
            false,
            true
        );
    }

}
