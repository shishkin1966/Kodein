package shishkin.sl.kodeinpsb.sl.specialist

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.AbsUnion
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.ShowDialogAction
import shishkin.sl.kodeinpsb.sl.state.State
import shishkin.sl.kodeinpsb.sl.ui.AbsActivity
import shishkin.sl.kodeinpsb.sl.ui.AbsContentActivity
import shishkin.sl.kodeinpsb.sl.ui.BackStack
import shishkin.sl.kodeinpsb.sl.ui.IActivity
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList


class ActivityUnion : AbsUnion<IActivity>(), IActivityUnion {
    companion object {
        const val NAME = "ActivityUnion"
    }

    private val activities = Collections.synchronizedList(ArrayList<WeakReference<IActivity>>())

    override fun register(subscriber: IActivity): Boolean {
        if (super.register(subscriber)) {
            for (i in activities.size - 1 downTo 0) {
                if (activities.get(i) == null) {
                    activities.removeAt(i)
                    continue
                }
                if (activities.get(i).get() == null) {
                    activities.removeAt(i)
                    continue
                }

                if (activities.get(i).get()!!.getName().equals(subscriber.getName())) {
                    if (!activities.get(i).get()!!.equals(subscriber)) {
                        activities.get(i).get()!!.stop()
                    }
                    activities.removeAt(i)
                }
            }

            activities.add(WeakReference(subscriber))
            return true
        }
        return false
    }

    override fun unregister(subscriber: IActivity) {
        super.unregister(subscriber)

        for (i in activities.size - 1 downTo 0) {
            if (activities.get(i) == null) {
                activities.removeAt(i)
                continue
            }
            if (activities.get(i).get() == null) {
                activities.removeAt(i)
                continue
            }

            if (activities.get(i).get()!!.equals(subscriber)) {
                activities.removeAt(i)
            }
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun stop() {
        for (ref in activities) {
            if (ref != null && ref.get() != null) {
                ref.get()!!.stop()
            }
        }
    }

    override fun onUnRegisterLastSubscriber() {
        if (ApplicationSpecialist.instance.isExit()) {
            if (ApplicationSpecialist.instance.isKillOnFinish()) {
                val serviceLocator = ApplicationSpecialist.serviceLocator
                if (serviceLocator != null) {
                    for (specialist in serviceLocator.getSpecialists()) {
                        if (specialist !is IActivityUnion && specialist !is IApplicationSpecialist) {
                            specialist.stop()
                        }
                    }
                }
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
    }

    override fun <F> getFragment(cls: Class<F>, id: Int): F? {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.getFragment(activity, cls, id)
        } else null
    }

    override fun <F> getCurrentFragment(): F? {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.getCurrentFragment(activity)
        } else null
    }

    override fun isBackStackEmpty(): Boolean {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.isBackStackEmpty(activity)
        } else true
    }

    override fun getBackStackEntryCount(): Int {
        val activity = getActivity<AppCompatActivity>()
        if (activity != null) {
            BackStack.getBackStackEntryCount(activity)
        }
        return 0
    }

    override fun switchToTopFragment() {
        val activity = getActivity<AppCompatActivity>()
        if (activity != null) {
            BackStack.switchToTopFragment(activity)
        }
    }

    override fun hasTop(): Boolean {
        val activity = getActivity<AppCompatActivity>()
        return if (activity != null) {
            BackStack.hasTopFragment(activity)
        } else false
    }

    override fun <C> getActivity(): C? {
        var subscriber: IActivity? = getCurrentSubscriber()
        if (subscriber != null) {
            return subscriber as C?
        }
        if (getSubscribers().size == 1) {
            subscriber = getSubscribers()[0]
            return subscriber as C?
        }
        return null
    }

    override fun <C> getActivity(name: String): C? {
        return getActivity<C>(name, false)
    }

    override fun <C> getActivity(name: String, validate: Boolean): C? {
        val subscriber = getSubscriber(name)
        if (subscriber != null) {
            if (!validate || validate && subscriber.validate()) {
                return subscriber as C?
            }
        }
        return null
    }

    override fun switchToFragment(name: String) {
        val subscriber = getCurrentSubscriber()
        if (subscriber is AbsContentActivity) {
            subscriber.switchToFragment(name)
        }
    }

    override fun back() {
        val subscriber = getCurrentSubscriber()
        if (subscriber != null) {
            if (subscriber is AbsActivity) {
                subscriber.onBackPressed()
            }
        }
    }

    override fun grantPermission(listener: String, permission: String, helpMessage: String) {
        if (ApplicationUtils.hasMarshmallow()) {
            val subscriber = getCurrentSubscriber()
            if (subscriber != null && subscriber.validate() && subscriber.getState() == State.STATE_READY) {
                val activity = subscriber as AppCompatActivity
                if (activity != null) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            permission
                        )
                    ) {
                        addAction(
                            ShowDialogAction(
                                R.id.dialog_request_permissions,
                                listener,
                                null,
                                helpMessage
                            ).setPositiveButton(R.string.setting).setNegativeButton(R.string.cancel).setCancelable(
                                false
                            )
                        )
                    } else {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(permission),
                            ApplicationUtils.REQUEST_PERMISSIONS
                        )
                    }
                }
            }
        }
    }

    override fun grantPermission(permission: String) {
        if (ApplicationUtils.hasMarshmallow()) {
            val subscriber = getCurrentSubscriber()
            if (subscriber != null && subscriber.validate() && subscriber.getState() == State.STATE_READY) {
                val activity = subscriber as AppCompatActivity
                if (activity != null) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            permission
                        )
                    ) {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(permission),
                            ApplicationUtils.REQUEST_PERMISSIONS
                        )
                    }
                }
            }
        }
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is IActivityUnion) 0 else 1
    }

    override fun addAction(action: IAction) {
        val subscriber = getCurrentSubscriber()
        if (subscriber != null && subscriber.validate()) {
            subscriber.addAction(action)
        }
    }


}
