package shishkin.sl.kotlin.app.screen.main

import android.content.Intent
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationConstant
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.action.HideSideMenuAction
import shishkin.sl.kotlin.app.provider.UseCasesProvider
import shishkin.sl.kotlin.sl.action.ApplicationAction
import shishkin.sl.kotlin.sl.action.DataAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.SnackBarAction
import shishkin.sl.kotlin.sl.presenter.AbsModelPresenter
import shishkin.sl.kotlin.sl.provider.ApplicationProvider

class MainPresenter(model: MainModel) : AbsModelPresenter(model) {
    companion object {
        const val NAME = "MainPresenter"

        const val IntentAction = "IntentAction"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                IntentAction -> {
                    parseIntent(action.getData() as Intent)
                    return true
                }
            }
        }

        if (action is SnackBarAction) {
            if (action.getName() == ApplicationProvider.appContext.getString(R.string.exit)) {
                ApplicationSingleton.instance.getUseCase().addAction(
                    ApplicationAction(
                        UseCasesProvider.OnExitAction
                    )
                )
            }
            return true
        }

        if (action is HideSideMenuAction) {
            getView<MainActivity>().addAction(action)
            return true
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    private fun parseIntent(intent: Intent) {
        val router = ApplicationSingleton.instance.getRouter()
        when (intent.action) {
            "android.intent.action.MAIN" -> {
                router.showRootFragment()
            }
            ApplicationConstant.ACTION_CLICK -> {
                if (router.hasTopFragment()) {
                    router.switchToTopFragment()
                } else {
                    router.showRootFragment()
                }
            }
        }
        getView<MainActivity>().clearIntent()
    }

    override fun onStart() {
        ApplicationSingleton.instance.getNotification()
            .replaceNotification(message = "Старт приложения")
    }

}
