package shishkin.sl.kotlin.app.screen.scanner2

import android.Manifest
import com.google.android.gms.vision.barcode.Barcode
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.sl.action.DataAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.PermissionAction
import shishkin.sl.kotlin.sl.presenter.AbsModelPresenter
import shishkin.sl.kotlin.sl.provider.ApplicationProvider


class Scanner2Presenter(model: Scanner2Model) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "Scanner2Presenter"
        const val OnDetections = "OnDetections"
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
                OnDetections -> {
                    val barcode = action.getData() as Barcode
                    if (!barcode.displayValue.isNullOrEmpty()) {
                        ApplicationUtils.showToast(
                            ApplicationProvider.appContext,
                            barcode.displayValue
                        )
                    }
                    return true
                }
            }
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    override fun onStart() {
        if (!ApplicationUtils.checkPermission(
                ApplicationProvider.appContext,
                Manifest.permission.CAMERA
            )
        ) {
            getView<Scanner2Fragment>().addAction(PermissionAction(Manifest.permission.CAMERA))
        }
    }

}
