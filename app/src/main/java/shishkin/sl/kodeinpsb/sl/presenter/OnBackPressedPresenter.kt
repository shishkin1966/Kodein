package shishkin.sl.kodeinpsb.sl.presenter

import com.google.android.material.snackbar.Snackbar
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.action.HideSideMenuAction
import shishkin.sl.kodeinpsb.app.screen.main.MainPresenter
import shishkin.sl.kodeinpsb.sl.action.ShowSnackbarAction
import shishkin.sl.kodeinpsb.sl.specialist.ActivityUnion
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import java.util.*


class OnBackPressedPresenter : AbsPresenter() {
    companion object {
        const val NAME = "OnBackPressedPresenter"
    }

    private var doubleBackPressedOnce = false
    private var timer: Timer? = null

    override fun getName(): String {
        return NAME
    }

    override fun isRegister(): Boolean {
        return false
    }

    fun onClick(): Boolean {
        if (isValid()) {
            val presenter =
                ApplicationSingleton.instance.getPresenter<MainPresenter>(MainPresenter.NAME)
            if (presenter != null && presenter.isMenuShowing()) {
                presenter.addAction(HideSideMenuAction())
                return true
            }
            if (!doubleBackPressedOnce) {
                val context = ApplicationSpecialist.appContext
                doubleBackPressedOnce = true
                val union =
                    ApplicationSpecialist.serviceLocator?.get<ActivityUnion>(ActivityUnion.NAME)
                if (union != null) {
                    union.getCurrentSubscriber()?.addAction(
                        ShowSnackbarAction(context.getString(R.string.double_back_pressed)).setDuration(
                            Snackbar.LENGTH_SHORT
                        ).setAction(ApplicationSpecialist.appContext.getString(R.string.exit))
                    )
                }
                startTimer()
            } else {
                ApplicationSpecialist.instance.stop()
                return true
            }
        }
        return false
    }

    private fun startTimer() {
        if (timer != null) {
            stopTimer()
        }
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                doubleBackPressedOnce = false
            }
        }, 2000)
    }

    private fun stopTimer() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        stopTimer()
    }

}
