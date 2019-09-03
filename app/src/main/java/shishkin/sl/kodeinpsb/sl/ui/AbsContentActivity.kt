package shishkin.sl.kodeinpsb.sl.ui

import shishkin.sl.kodeinpsb.sl.IRouter
import androidx.fragment.app.Fragment
import shishkin.sl.kodeinpsb.sl.action.HideKeyboardAction


abstract class AbsContentActivity : AbsActivity(), IRouter {

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

}