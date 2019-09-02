package shishkin.sl.kodeinpsb.sl.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import shishkin.sl.kodeinpsb.sl.ISubscriber


class BackStack {
    companion object {
        /**
         * Показать фрагмент
         */
        @JvmStatic
        fun showFragment(
            activity: AppCompatActivity, idRes: Int, fragment: Fragment, addToBackStack: Boolean,
            clearBackStack: Boolean,
            animate: Boolean, allowingStateLoss: Boolean
        ) {
            val tag: String?
            val fm = activity.supportFragmentManager
            if (fragment is ISubscriber) {
                tag = (fragment as ISubscriber).getName()
            } else {
                tag = fragment::class.simpleName
            }
            if (clearBackStack) {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            val ft = fm.beginTransaction()
            if (addToBackStack) {
                ft.addToBackStack(tag)
            }
            if (animate) {
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
            ft.replace(idRes, fragment, tag)
            if (allowingStateLoss) {
                ft.commitAllowingStateLoss()
            } else {
                ft.commit()
            }
        }

        /**
         * Переключиться на фрагмент верхнего уровня
         */
        @JvmStatic
        fun switchToTopFragment(activity: AppCompatActivity) {
            val fm = activity.supportFragmentManager
            val backStackEntryCount = fm.backStackEntryCount
            if (backStackEntryCount > 0) {
                for (i in backStackEntryCount - 1 downTo 0) {
                    val backStackEntry = fm.getBackStackEntryAt(i)
                    val fragment = fm.findFragmentByTag(backStackEntry.name)
                    if (fragment is OnBackPressListener) {
                        if ((fragment as OnBackPressListener).isTop()) {
                            fm.popBackStackImmediate(backStackEntry.name, 0)
                        }
                    }
                }
            }
        }

    }


}
