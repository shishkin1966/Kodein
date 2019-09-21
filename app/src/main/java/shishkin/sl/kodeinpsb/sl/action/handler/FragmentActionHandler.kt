package shishkin.sl.kodeinpsb.sl.action.handler

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.common.KeyboardRunnable
import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.action.*


class FragmentActionHandler(private val fragment: Fragment) : BaseActionHandler() {
    override fun onAction(action: IAction): Boolean {
        if (fragment is IValidated && !fragment.isValid()) return false

        if (super.onAction(action)) return true

        if (action is HideProgressBarAction) {
            hideProgressBar()
            return true
        }
        if (action is ShowProgressBarAction) {
            showProgressBar()
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

        return false
    }

    private fun showProgressBar() {
        val view = fragment.view?.findViewById<View>(R.id.progressBar)
        view?.setVisibility(View.VISIBLE)
    }

    private fun hideProgressBar() {
        val view = fragment.view?.findViewById<View>(R.id.progressBar)
        view?.setVisibility(View.INVISIBLE)
    }

    private fun showKeyboard(action: ShowKeyboardAction) {
        val activity = fragment.getActivity() ?: return

        KeyboardRunnable(activity, action.getView()).run()
    }

    private fun hideKeyboard() {
        val activity = fragment.getActivity() ?: return
        if (activity.isFinishing()) return

        val imm = ApplicationUtils.getSystemService<InputMethodManager>(
            activity,
            Activity.INPUT_METHOD_SERVICE
        )
        var view = activity.getCurrentFocus()
        if (view == null) {
            view = getRootView()
        }
        if (view != null) {
            activity.window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun getRootView(): View? {
        val view = fragment.view?.findViewById<View>(R.id.root)
        return if (view != null) {
            view
        } else fragment.getView()
    }

}
