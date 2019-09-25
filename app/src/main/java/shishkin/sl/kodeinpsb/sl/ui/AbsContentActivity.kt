package shishkin.sl.kodeinpsb.sl.ui

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.IRouter
import shishkin.sl.kodeinpsb.sl.action.HideKeyboardAction
import shishkin.sl.kodeinpsb.sl.observe.NetObservable
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber
import shishkin.sl.kodeinpsb.sl.specialist.ObservableUnion


abstract class AbsContentActivity : AbsActivity(), IRouter, IObservableSubscriber {
    private var snackbar: Snackbar? = null

    override fun onPause() {
        super.onPause()

        addAction(HideKeyboardAction())
    }

    override fun showFragment(fragment: Fragment) {
        showFragment(fragment, true, false, true, false)
    }

    override fun showFragment(fragment: Fragment, allowingStateLoss: Boolean) {
        showFragment(fragment, true, false, true, allowingStateLoss)
    }

    override fun showFragment(
        fragment: Fragment, addToBackStack: Boolean,
        clearBackStack: Boolean,
        animate: Boolean, allowingStateLoss: Boolean
    ) {

        BackStack.showFragment(
            this,
            getContentResId(),
            fragment,
            addToBackStack,
            clearBackStack,
            animate,
            allowingStateLoss
        )
    }

    override fun switchToFragment(name: String): Boolean {
        return BackStack.switchToFragment(this, name)
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate. Notice that you should add any [Fragment] that implements
     * [OnBackPressListener] to the back stack if you want [OnBackPressListener.onBackPressed]
     * to be invoked.
     */
    override fun onBackPressed() {
        BackStack.onBackPressed(this)
    }

    override fun switchToTopFragment() {
        BackStack.switchToTopFragment(this)
    }

    override fun hasTopFragment(): Boolean {
        return BackStack.hasTopFragment(this)
    }

    override fun isBackStackEmpty(): Boolean {
        return BackStack.isBackStackEmpty(this)
    }

    override fun getBackStackEntryCount(): Int {
        return BackStack.getBackStackEntryCount(this)
    }

    override fun <F> getContentFragment(cls: Class<F>): F? {
        return getFragment(cls, getContentResId())
    }

    override fun <F> getFragment(cls: Class<F>, id: Int): F? {
        return BackStack.getFragment(this, cls, id)
    }

    override fun onPermissionGranted(permission: String) {
        super.onPermissionGranted(permission)

        val fragment = getContentFragment(AbsFragment::class.java)
        fragment?.onPermissionGranted(permission)
    }

    override fun onPermissionDenied(permission: String) {
        super.onPermissionDenied(permission)

        val fragment = getContentFragment(AbsFragment::class.java)
        fragment?.onPermissionDenied(permission)
    }

    override fun getSpecialistSubscription(): List<String> {
        val list = ArrayList<String>()
        list.addAll(super.getSpecialistSubscription())
        list.add(ObservableUnion.NAME)
        return list
    }

    override fun getObservable(): List<String> {
        return listOf(NetObservable.NAME)
    }

    override fun onChange(name: String, obj: Any) {
        if (name == NetObservable.NAME) {
            if (obj == true) {
                onConnected()
            } else if (obj == false) {
                onDisConnected()
            }
        }
    }

    private fun onConnected() {
        if (snackbar?.isShown == true) {
            snackbar?.dismiss()
            snackbar = null
        }
    }

    private fun onDisConnected() {
        if (snackbar != null) {
            snackbar?.dismiss()
            snackbar = null
        }
        snackbar = ApplicationUtils.showSnackbar(
            getRootView(),
            getString(R.string.network_disconnected),
            Snackbar.LENGTH_INDEFINITE,
            ApplicationUtils.MESSAGE_TYPE_WARNING
        )
    }

    override fun getContentResId(): Int {
        return R.id.content
    }

    override fun isCurrentFragment(name: String): Boolean {
        return BackStack.isCurrentFragment(this, name)
    }

    override fun toBackground() {
        moveTaskToBack(true);
    }


}
