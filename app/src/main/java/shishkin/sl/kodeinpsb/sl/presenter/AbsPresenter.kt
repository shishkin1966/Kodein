package shishkin.sl.kodeinpsb.sl.presenter

import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.PresenterActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.state.StateObserver
import java.util.*
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton
import shishkin.sl.kodeinpsb.sl.specialist.MessengerUnion
import shishkin.sl.kodeinpsb.sl.specialist.PresenterUnion
import shishkin.sl.kodeinpsb.sl.state.State


abstract class AbsPresenter : IPresenter {
    private val model: IModel
    private val lifecycle = StateObserver(this)
    private val actions = LinkedList<IAction>()
    private val actionHandler = PresenterActionHandler(this)

    constructor(model: IModel) {
        this.model = model
    }

    override fun getState(): Int {
        return lifecycle.getState()
    }

    override fun setState(state: Int) {
        lifecycle.setState(state)
    }

    override fun onCreateView() {}

    override fun onReadyView() {
        ServiceLocatorSingleton.instance.register(this)

        doActions()

        onStart()
    }

    override fun onDestroyView() {
        ServiceLocatorSingleton.instance.unregister(this)

        onStop()
    }

    override fun <M:IModel> getModel(): M? {
        return model as M
    }

    override fun validate(): Boolean {
        return lifecycle.getState() != State.STATE_DESTROY
    }

    override fun getSpecialistSubscription(): List<String> {
        return listOf(PresenterUnion.NAME, MessengerUnion.NAME)
    }

    override fun onStart() {}

    override fun onStop() {}

    override fun <C : IModelView> getView(): C? {
        return if (model != null) {
            model?.getView()
        } else null
    }

    override fun read(message: IMessage) {}

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

    override fun onAction(action: IAction): Boolean {
        if (!validate()) return false

        if (actionHandler.onAction(action)) {
            return true
        }
        ErrorSpecialistSingleton.instance.onError(getName(), "Unknown action:" + action.toString(), true)
        return false
    }

}
