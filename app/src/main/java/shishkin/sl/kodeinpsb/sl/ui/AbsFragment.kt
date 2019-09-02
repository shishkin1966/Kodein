package shishkin.sl.kodeinpsb.sl.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.state.IStateable
import shishkin.sl.kodeinpsb.sl.state.State
import shishkin.sl.kodeinpsb.sl.state.StateObservable
import java.util.*






abstract class AbsFragment<M : IModel> : Fragment(), IFragment<M> {


    private val stateObservable = StateObservable(State.STATE_CREATE)
    private var model: M? = null
    private val actions = LinkedList<IAction>()

    override fun getModel(): M {
        if (model == null) {
            model = createModel()
        }
        return model!!
    }

    abstract fun createModel(): M

    override fun setModel(model: M) {
        this.model = model
    }

    override fun <V : View> findView(@IdRes id: Int): V? {
        val root = view
        return if (root != null) {
            ApplicationUtils.findView(root, id)
        } else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setModel(createModel())

        stateObservable.setState(State.STATE_CREATE)

        if (this is ISpecialistSubscriber) {
            ServiceLocatorSingleton.instance.register(this)
        }
    }

    override fun onStart() {
        super.onStart()

        doActions()

        getModel().addStateObserver()

        stateObservable.setState(State.STATE_READY)
    }

    override fun onDestroy() {
        super.onDestroy()

        stateObservable.setState(State.STATE_DESTROY)
        stateObservable.clear()

        if (this is ISpecialistSubscriber) {
            ServiceLocatorSingleton.instance.unregister(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        stateObservable.setState(State.STATE_NOT_READY)
    }

    override fun getState(): Int {
        return stateObservable.getState()
    }

    override fun setState(state: Int) {}

    override fun exit() {}

    override fun validate(): Boolean {
        return getState() != State.STATE_DESTROY
    }

    override fun getRootView(): View? {
        val view = findView<View>(R.id.root)
        return if (view != null) {
            view
        } else getView()
    }

    override fun addStateObserver(stateable: IStateable) {
        stateObservable.addObserver(stateable)
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

    override fun onPermissionGranted(permission: String) {}

    override fun onPermissionDenied(permission: String) {}

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
        for (event in deleted) {
            actions.remove(event)
        }
    }

}
