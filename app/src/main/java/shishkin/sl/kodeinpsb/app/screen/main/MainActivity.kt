package shishkin.sl.kodeinpsb.app.screen.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.app.action.HideSideMenuAction
import shishkin.sl.kodeinpsb.app.presenter.OnBackPressedPresenter
import shishkin.sl.kodeinpsb.app.screen.accounts.AccountsFragment
import shishkin.sl.kodeinpsb.app.screen.sidemenu.SideMenuFragment
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.common.SlidingMenu
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.ActivityActionHandler
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IErrorSpecialist
import shishkin.sl.kodeinpsb.sl.ui.AbsContentActivity
import shishkin.sl.kodeinpsb.sl.ui.BackStack

class MainActivity : AbsContentActivity() {
    private val actionHandler = ActivityActionHandler(this)
    private var menu: SlidingMenu? = null
    private val onBackPressedPresenter =
        OnBackPressedPresenter()

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (actionHandler.onAction(action)) return true

        if (action is HideSideMenuAction) {
            if (menu != null) {
                if (menu!!.isMenuShowing) {
                    menu?.showContent();
                }
            }
            return true
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    override fun createModel(): MainModel {
        return MainModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setMenu()

        onNewIntent(intent)
    }


    override fun getContentResId(): Int {
        return R.id.content
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        this.intent = intent
    }

    override fun onStart() {
        super.onStart()

        addStateObserver(onBackPressedPresenter)

        ApplicationUtils.grantPermisions(
            permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            activity = this
        )

        if (intent != null) {
            val action = intent.action
            if ("android.intent.action.MAIN" == action) {
                showHomeFragment();
            } else {
                showHomeFragment();
            }
            intent = null
        } else {
            showHomeFragment()
        }
    }

    private fun showHomeFragment() {
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

    private fun setMenu() {
        if (menu == null) {
            menu = SlidingMenu(this)
            menu?.mode = SlidingMenu.LEFT
            menu?.touchModeAbove = SlidingMenu.TOUCHMODE_MARGIN
            menu?.setShadowWidthRes(R.dimen.dimen_4dp)
            menu?.setBehindOffsetRes(R.dimen.slidingmenu_offset)
            menu?.setShadowDrawable(R.drawable.shadow)
            menu?.setFadeDegree(0.35f)
            menu?.attachToActivity(this, SlidingMenu.SLIDING_CONTENT)
            menu?.setMenu(R.layout.menu_container)
        }

        BackStack.showFragment(
            activity = this,
            idRes = R.id.menu,
            fragment = SideMenuFragment.newInstance(),
            addToBackStack = false,
            clearBackStack = false,
            animate = false,
            allowingStateLoss = true
        )
    }

    fun isMenuShowing(): Boolean {
        if (menu != null) {
            return menu!!.isMenuShowing
        }
        return false
    }

    override fun onBackPressed() {
        if (menu != null && menu!!.isMenuShowing) {
            menu?.showContent()
            return
        }
        val fragment =
            ApplicationSingleton.instance.getActivityUnion().getCurrentFragment<Fragment>()
        if (fragment !is AccountsFragment) {
            super.onBackPressed()
            return
        }
        if (!onBackPressedPresenter.onClick()) {
            super.onBackPressed()
        }
    }
}
