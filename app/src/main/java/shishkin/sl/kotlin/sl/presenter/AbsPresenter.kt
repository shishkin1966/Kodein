package shishkin.sl.kotlin.sl.presenter

import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.message.IMessage
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.provider.IMessengerUnion
import shishkin.sl.kotlin.sl.provider.MessengerUnion
import shishkin.sl.kotlin.sl.provider.PresenterUnion
import shishkin.sl.kotlin.sl.state.State
import shishkin.sl.kotlin.sl.state.StateObserver
import java.util.*


abstract class AbsPresenter() : IPresenter {
    private val lifecycle = StateObserver(this)
    private val actions = LinkedList<IAction>()

    override fun getState(): Int {
        return lifecycle.getState()
    }

    override fun setState(state: Int) {
        lifecycle.setState(state)
    }

    override fun onCreateView() {}

    override fun onReadyView() {
        ApplicationProvider.serviceLocator?.registerSubscriber(this)

        doActions()

        ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
            MessengerUnion.NAME
        )
            ?.readMessages(this)

        onStart()
    }

    open fun onStart() {
    }

    override fun onDestroyView() {
        ApplicationProvider.serviceLocator?.unregisterSubscriber(this)
    }

    override fun isValid(): Boolean {
        return lifecycle.getState() != State.STATE_DESTROY
    }

    override fun getProviderSubscription(): List<String> {
        return listOf(PresenterUnion.NAME, MessengerUnion.NAME)
    }

    override fun onStopProvider(provider: IProvider) {
    }

    override fun read(message: IMessage) {}

    override fun addAction(action: IAction) {
        when (getState()) {
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
            if (getState() != State.STATE_READY) {
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
        return true
    }

}
