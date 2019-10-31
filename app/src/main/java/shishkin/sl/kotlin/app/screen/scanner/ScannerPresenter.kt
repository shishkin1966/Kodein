package shishkin.sl.kotlin.app.screen.scanner

import android.Manifest
import android.util.SparseArray
import androidx.core.util.forEach
import com.google.android.gms.vision.barcode.Barcode
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.sl.action.DataAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.PermissionAction
import shishkin.sl.kotlin.sl.presenter.AbsModelPresenter
import shishkin.sl.kotlin.sl.provider.ApplicationProvider


class ScannerPresenter(model: ScannerModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "AccountsPresenter"
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
                    val barcodes = action.getData() as SparseArray<Barcode>
                    barcodes.forEach { _, barcode ->
                        if (!barcode.displayValue.isNullOrEmpty()) {
                            ApplicationUtils.showToast(
                                ApplicationProvider.appContext,
                                barcode.displayValue
                            )
                        }
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
            getView<ScannerFragment>().addAction(PermissionAction(Manifest.permission.CAMERA))
        }
    }

}
