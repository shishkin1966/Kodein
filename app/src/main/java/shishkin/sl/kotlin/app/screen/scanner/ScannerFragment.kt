package shishkin.sl.kotlin.app.screen.scanner

import android.Manifest
import android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.sl.action.DataAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.PermissionAction
import shishkin.sl.kotlin.sl.model.IModel
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.ui.AbsContentFragment
import java.io.IOException


class ScannerFragment : AbsContentFragment() {
    companion object {
        const val NAME = "ScannerFragment"

        fun newInstance(): ScannerFragment {
            return ScannerFragment()
        }
    }

    private var isBackPress = false
    private lateinit var cameraView: SurfaceView
    private val detector = BarcodeDetector.Builder(ApplicationProvider.appContext)
        .setBarcodeFormats(Barcode.ALL_FORMATS).build()
    private val cameraSource =
        CameraSource.Builder(ApplicationProvider.appContext, detector)
            .setFocusMode(FOCUS_MODE_CONTINUOUS_PICTURE)
            .setAutoFocusEnabled(true)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedFps(15.0f)
            .setRequestedPreviewSize(1024, 768)
            .build()

    private val callBack = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                cameraSource.start(cameraView.holder)
            } catch (e: IOException) {
                ApplicationSingleton.instance.onError(getName(), e)
            }
        }

        override fun surfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            try {
                cameraSource.release()
                cameraView.holder.removeCallback(this)
                if (!isBackPress) {
                    ApplicationSingleton.instance.routerProvider.onBackPressed()
                }
            } catch (e: Exception) {
                ApplicationSingleton.instance.onError(getName(), e)
            }
        }
    }

    override fun createModel(): IModel {
        return ScannerModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraView = view.findViewById(R.id.cameraView)

        initScanner()
    }

    private fun initScanner() {
        if (!ApplicationUtils.checkPermission(
                ApplicationProvider.appContext,
                Manifest.permission.CAMERA
            )
        ) {
            return
        }

        cameraView.holder.addCallback(callBack)

        detector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    getModel<ScannerModel>().getPresenter<ScannerPresenter>()
                        .addAction(DataAction(ScannerPresenter.OnDetections, barcodes))
                }
            }
        })
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is PermissionAction) {
            ApplicationUtils.grantPermissions(
                arrayOf(Manifest.permission.CAMERA),
                this
            )
            return true
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    override fun onPermissionGranted(permission: String) {
        when (permission) {
            Manifest.permission.CAMERA -> {
                ApplicationUtils.showToast(
                    ApplicationProvider.appContext,
                    "Повторно зайдите в сканер",
                    Toast.LENGTH_SHORT,
                    ApplicationUtils.MESSAGE_TYPE_INFO
                )
                ApplicationSingleton.instance.routerProvider.onBackPressed()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        isBackPress = true
        return false
    }


}


