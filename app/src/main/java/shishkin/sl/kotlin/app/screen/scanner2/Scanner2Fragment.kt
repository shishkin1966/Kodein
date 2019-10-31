package shishkin.sl.kotlin.app.screen.scanner2

import android.Manifest
import android.hardware.Camera
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.common.scanner.*
import shishkin.sl.kotlin.sl.action.DataAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.PermissionAction
import shishkin.sl.kotlin.sl.model.IModel
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.ui.AbsContentFragment
import java.io.IOException


class Scanner2Fragment : AbsContentFragment(), BarcodeGraphicTracker.BarcodeListener {
    companion object {
        const val NAME = "Scanner2Fragment"
        const val RC_HANDLE_GMS = 9001

        fun newInstance(): Scanner2Fragment {
            return Scanner2Fragment()
        }
    }

    private lateinit var preview: CameraSourcePreview
    private lateinit var graphicOverlay: GraphicOverlay<BarcodeGraphic>
    private var cameraSource: CameraSource? = null

    override fun createModel(): IModel {
        return Scanner2Model(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scanner2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preview = view.findViewById(R.id.preview)
        graphicOverlay = view.findViewById(R.id.graphicOverlay)

        if (ApplicationUtils.checkPermission(
                ApplicationProvider.appContext,
                Manifest.permission.CAMERA
            )
        ) {
            createCameraSource()
        }
    }

    private fun createCameraSource() {
        val barcodeDetector = BarcodeDetector.Builder(ApplicationProvider.appContext).build()
        val barcodeFactory = BarcodeTrackerFactory(graphicOverlay, this)
        barcodeDetector.setProcessor(
            MultiProcessor.Builder<Barcode>(barcodeFactory).build()
        )
        if (!barcodeDetector.isOperational) {
            ApplicationUtils.showToast(
                ApplicationProvider.appContext,
                "Подождите пока загрузятся библиотеки распознования баркодов"
            )
        }
        cameraSource = CameraSource.Builder(ApplicationProvider.appContext, barcodeDetector)
            .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
            .setAutoFocusEnabled(true)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1600, 1024)
            .setRequestedFps(15.0f)
            .build()
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
                createCameraSource()
                startCameraSource()
            }
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun onResume() {
        super.onResume()

        try {
            startCameraSource()
        } catch (e: Exception) {
            ApplicationSingleton.instance.onError(getName(), e)
        }
    }

    override fun onPause() {
        super.onPause()

        preview.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

        preview.release()
    }

    @Throws(SecurityException::class)
    private fun startCameraSource() {
        val code = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(ApplicationProvider.appContext)
        if (code != ConnectionResult.SUCCESS) {
            val dlg =
                GoogleApiAvailability.getInstance().getErrorDialog(activity, code, RC_HANDLE_GMS)
            dlg.show()
        }

        if (cameraSource != null) {
            try {
                preview.start(cameraSource, graphicOverlay)
            } catch (e: IOException) {
                cameraSource?.release()
                cameraSource = null
            }
        }
    }

    override fun onDetected(data: Barcode?) {
        if (data != null) {
            getModel<Scanner2Model>().getPresenter<Scanner2Presenter>()
                .addAction(DataAction(Scanner2Presenter.OnDetections, data))
        }
    }
}


