package shishkin.sl.kodeinpsb.sl.observe

import shishkin.sl.kodeinpsb.sl.IProvider
import shishkin.sl.kodeinpsb.sl.provider.IObservableSubscriber
import shishkin.sl.kodeinpsb.sl.provider.ObservableUnion
import shishkin.sl.kodeinpsb.sl.state.State

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
