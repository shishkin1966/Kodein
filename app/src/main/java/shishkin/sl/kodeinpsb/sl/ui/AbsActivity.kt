package shishkin.sl.kodeinpsb.sl.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.specialist.ActivityUnion
import shishkin.sl.kodeinpsb.sl.state.IStateable
import shishkin.sl.kodeinpsb.sl.state.State
import shishkin.sl.kodeinpsb.sl.state.StateObservable
import java.util.*


abstract class AbsActivity : AppCompatActivity(), IActivity {

    private val stateObservable = StateObservable(State.STATE_CREATE)
    private var model: IModel? = null
    private val actions = LinkedList<IAction>()

    override fun <T : IModel> getModel(): T? {
        if (model == null) {
            model = createModel();
        }
        return model as T
    }

    override fun <T : IModel> setModel(model: T) {
        this.model = model
    }

    abstract fun createModel(): IModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        setModel(createModel())

        stateObservable.setState(State.STATE_CREATE)

        ServiceLocatorSingleton.instance.registerSpecialistSubscriber(this)
    }

    override fun <V : View> findView(@IdRes id: Int): V? {
        return ApplicationUtils.findView(this, id)
    }

    override fun onStart() {
        super.onStart()

        doActions()

        (getModel<IModel>() as IModel).addStateObserver();

        stateObservable.setState(State.STATE_READY);
    }

    override fun onDestroy() {
        super.onDestroy()

        stateObservable.setState(State.STATE_DESTROY)
        stateObservable.clear()

        ServiceLocatorSingleton.instance.unregister(this)
    }

    override fun onResume() {
        super.onResume()

        ServiceLocatorSingleton.instance.setCurrentSubscriber(this)
    }

    override fun getSpecialistSubscription(): List<String> {
        return listOf(ActivityUnion.NAME)
    }

    override fun clearBackStack() {
        BackStack.clearBackStack(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: kotlin.IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (i in permissions.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permissions[i])
            } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                onPermissionDenied(permissions[i])
            }
        }
    }

    override fun getState(): Int {
        return stateObservable.getState()
    }

    override fun setState(state: Int) {}
    override fun validate(): Boolean {
        return getState() !== State.STATE_DESTROY && !isFinishing
    }

    override fun exit() {
        if (ApplicationUtils.hasJellyBean()) {
            super.finishAffinity()
        } else {
            super.finish()
        }
    }

    override fun onPermissionGranted(permission: String) {}

    override fun onPermissionDenied(permission: String) {}

    override fun getRootView(): View? {
        val view = findView<View>(R.id.root)
        return if (view != null) {
            return view
        } else (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key. The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    fun onActivityBackPressed() {
        super.onBackPressed()
    }

    override fun addStateObserver(stateable: IStateable) {
        stateObservable.addObserver(stateable)
    }

    override fun addAction(action: IAction) {
        val state = getState()
        when (state) {
            State.STATE_DESTROY -> return

            State.STATE_CREATE, State.STATE_NOT_READY -> {
                actions.add(action)
                return
            }

            else -> {
                actions.add(action)
                doActions()
            }
        }
    }

    private fun doActions() {
        val deleted = ArrayList<IAction>()
        for (i in actions.indices) {
            if (getState() !== State.STATE_READY) {
                break
            }
            onAction(actions[i])
            deleted.add(actions[i])
        }
        for (action in deleted) {
            actions.remove(action)
        }
    }

    override fun onStop(specialist: ISpecialist) {
    }
}
