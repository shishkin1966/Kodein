package shishkin.sl.kodeinpsb.sl.action.handler

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import microservices.shishkin.sl.ui.MaterialDialogExt
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.common.BaseSnackbar
import shishkin.sl.kodeinpsb.common.KeyboardRunnable
import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.action.*
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter
import shishkin.sl.kodeinpsb.sl.specialist.ActivityUnion
import shishkin.sl.kodeinpsb.sl.specialist.IActivityUnion
import shishkin.sl.kodeinpsb.sl.ui.AbsActivity
import shishkin.sl.kodeinpsb.sl.ui.BackStack


class ActivityActionHandler(private val activity: AppCompatActivity) : BaseActionHandler() {
    private var snackbar: Snackbar? = null

    override fun onAction(action: IAction): Boolean {
        if (activity is IValidated) {
            if (!activity.validate()) return false
        }

        if (super.onAction(action)) return true

        if (action is ShowProgressBarAction) {
            showProgressBar()
            return true
        }
        if (action is HideProgressBarAction) {
            hideProgressBar()
            return true
        }
        if (action is ShowKeyboardAction) {
            showKeyboard(action)
            return true
        }
        if (action is HideKeyboardAction) {
            hideKeyboard()
            return true
        }
        if (action is LockOrientationAction) {
            lockOrientation(action)
            return true
        }
        if (action is UnLockOrientationAction) {
            unlockOrientation()
            return true
        }
        if (action is ShowSnackbarAction) {
            showSnackbar(action)
            return true
        }
        if (action is HideSnackbarAction) {
            hideSnackbar()
            return true
        }
        if (action is ShowDialogAction) {
            showDialog(action)
            return true
        }
        if (action is StartActivityAction) {
            if (action.getIntent().resolveActivity(activity.packageManager) != null) {
                activity.startActivity((action).getIntent())
            }
            return true
        }
        if (action is StartChooseActivityAction) {
            if (action.getIntent().resolveActivity(activity.packageManager) != null) {
                activity.startActivity(
                    Intent.createChooser(
                        action.getIntent(),
                        action.getTitle()
                    )
                )
            }
            return true
        }
        if (action is StartActivityForResultAction) {
            if (action.getIntent().resolveActivity(activity.packageManager) != null) {
                activity.startActivityForResult(
                    action.getIntent(),
                    action.getRequestCode()
                )
            }
            return true
        }
        if (action is ShowFragmentAction) {
            if (activity is AbsActivity) {
                BackStack.showFragment(
                    activity,
                    action.getIdRes(),
                    (action as ShowFragmentAction).getFragment(),
                    (action as ShowFragmentAction).isAddToBackStack(),
                    (action as ShowFragmentAction).isClearBackStack(),
                    (action as ShowFragmentAction).isAnimate(),
                    (action as ShowFragmentAction).isAllowingStateLoss()
                )
                return true
            }
        }
        if (action is PermissionAction) {
            if (action.getListener().isNullOrEmpty()) {
                grantPermission(action.getPermission())
            } else {
                grantPermission(
                    action.getPermission(),
                    action.getListener(),
                    action.getHelpMessage()
                )
            }
            return true
        }
        return false
    }

    private fun showProgressBar() {
        val view = activity.findViewById<View>(R.id.progressBar)
        if (view != null) {
            view.setVisibility(View.VISIBLE)
        }
    }

    private fun hideProgressBar() {
        val view = activity.findViewById<View>(R.id.progressBar)
        if (view != null) {
            view.setVisibility(View.INVISIBLE)
        }
    }

    private fun showKeyboard(action: ShowKeyboardAction) {
        KeyboardRunnable(activity, action.getView()).run()
    }

    private fun hideKeyboard() {
        val imm = ApplicationUtils.getSystemService<InputMethodManager>(
            this.activity,
            Activity.INPUT_METHOD_SERVICE
        )
        var view = activity.currentFocus
        if (view == null) {
            view = getRootView()
        }
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getRootView(): View {
        return if (activity.findViewById<View>(R.id.root) != null) {
            activity.findViewById(R.id.root)
        } else (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }

    private fun lockOrientation(action: LockOrientationAction) {
        activity.requestedOrientation = action.getOrientation()
    }

    private fun unlockOrientation() {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun showSnackbar(action: ShowSnackbarAction) {
        val actionName = action.getAction()
        if (actionName.isNullOrEmpty()) {
            snackbar = BaseSnackbar().make(
                getRootView(), action.getMessage(), action
                    .getDuration(), action.getType()
            )
            snackbar?.show()
        } else {
            snackbar = BaseSnackbar().make(
                getRootView(),
                action.getMessage(),
                action.getDuration(),
                action.getType()
            )
                .setAction(actionName, this::onSnackbarClick)
            snackbar?.show()
        }
    }

    private fun onSnackbarClick(view: View) {
        var action: String? = null
        if (view is AppCompatButton) {
            action = view.text.toString()
        } else if (view is Button) {
            action = view.getText().toString()
        }
        if (activity is IModelView && !action.isNullOrBlank()) {
            val model = activity.getModel<IModel>()
            model?.getPresenter<IPresenter>()?.addAction(ApplicationAction(action))
        }
    }

    private fun hideSnackbar() {
        if (snackbar != null) {
            snackbar?.dismiss()
        }
    }

    private fun showDialog(action: ShowDialogAction) {
        MaterialDialogExt(
            activity,
            action.getListener()!!,
            action.getId(),
            action.getTitle()!!,
            action.getMessage()!!,
            action.getButtonPositive(),
            action.getButtonNegative(),
            action.isCancelable()
        ).show()
    }

    private fun grantPermission(permission: String) {
        if (ApplicationUtils.hasMarshmallow()) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                activity.requestPermissions(
                    arrayOf(permission),
                    ApplicationUtils.REQUEST_PERMISSIONS
                )
            }
        }
    }

    private fun grantPermission(permission: String, listener: String?, helpMessage: String?) {
        if (ApplicationUtils.hasMarshmallow()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                val union = ServiceLocatorSingleton.instance.get<IActivityUnion>(ActivityUnion.NAME)
                if (union != null) {
                    union.addAction(
                        ShowDialogAction(
                            R.id.dialog_request_permissions,
                            listener!!,
                            null,
                            helpMessage!!
                        ).setPositiveButton(R.string.setting).setNegativeButton(R.string.cancel).setCancelable(
                            false
                        )
                    )
                }
            } else {
                activity.requestPermissions(
                    arrayOf(permission),
                    ApplicationUtils.REQUEST_PERMISSIONS
                )
            }
        }
    }

}
