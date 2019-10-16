package shishkin.sl.kotlin.sl.observe

import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.provider.IObservableSubscriber
import shishkin.sl.kotlin.sl.provider.ObservableUnion
import shishkin.sl.kotlin.sl.state.State

abstract class AbsObservableSubscriber : IObservableSubscriber {
    override fun getState(): Int {
        return State.STATE_READY
    }

    override fun setState(state: Int) {
    }

    override fun getProviderSubscription(): List<String> {
        return listOf(ObservableUnion.NAME)
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun onStopProvider(provider: IProvider) {
    }
}
