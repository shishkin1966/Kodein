package shishkin.sl.kotlin.sl.ui

import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.sl.action.IAction

abstract class AbsContentFragment : AbsFragment(), OnBackPressListener {

    /**
     * @return true if fragment itself or its children correctly handle back press event.
     */
    override fun onBackPressed(): Boolean {
        var backPressedHandled = false

        val fragmentManager = childFragmentManager
        val children = fragmentManager.fragments
        for (child in children) {
            if (child != null && (child is OnBackPressListener)) {
                backPressedHandled =
                    backPressedHandled or (child as OnBackPressListener).onBackPressed()
            }
        }
        return backPressedHandled
    }

    override fun isTop(): Boolean {
        return false
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

}
