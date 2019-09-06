package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.AbsSmallUnion
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.Secretary
import shishkin.sl.kodeinpsb.sl.observe.IObservable


class ObservableUnion : AbsSmallUnion<IObservable>(), IObservableUnion {
    companion object {
        const val NAME = "ObservableUnion"
    }

    override fun createSecretary(): Secretary<IObservable> {
        return Secretary()
    }

    override fun getName(): String {
        return NAME
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is IObservableUnion) 0 else 1
    }

    override fun getObservables(): List<IObservable> {
        return getSubscribers()
    }

    override fun register(subscriber: IObservableSubscriber): Boolean {
        val list = subscriber.getObservable()
        for (observable in getObservables()) {
            val name = observable.getName()
            if (list.contains(name)) {
                observable.addObserver(subscriber)
            }
        }
        return true
    }

    override fun unregister(subscriber: IObservableSubscriber): Boolean {
        val list = subscriber.getObservable()
        for (observable in getObservables()) {
            if (list.contains(observable.getName())) {
                observable.removeObserver(subscriber)
            }
        }
        return true
    }

    override fun onUnRegister() {
        for (observable in getObservables()) {
            observable.unregister()
        }
    }

    override fun onChange(name: String, obj: Any) {
        val observable = getSubscriber(name)
        observable?.onChange(obj)
    }

}
