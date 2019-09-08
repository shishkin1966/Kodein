package shishkin.sl.kodeinpsb.sl.observe

import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber
import shishkin.sl.kodeinpsb.sl.specialist.ObservableUnion
import shishkin.sl.kodeinpsb.sl.state.State

abstract class AbsObservableSubscriber : IObservableSubscriber {
    override fun getState(): Int {
        return State.STATE_READY
    }

    override fun setState(state: Int) {
    }

    override fun getSpecialistSubscription(): List<String> {
        return listOf(ObservableUnion.NAME)
    }

    override fun validate(): Boolean {
        return true
    }

    override fun onStop(specialist: ISpecialist) {
    }
}
