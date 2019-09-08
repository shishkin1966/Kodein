package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.AbsSmallUnion
import shishkin.sl.kodeinpsb.sl.ISecretary
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.Secretary
import shishkin.sl.kodeinpsb.sl.observe.IObservable


class ObservableUnion : AbsSmallUnion<IObservableSubscriber>(), IObservableUnion {
    private val secretary = Secretary<IObservable>()

    companion object {
        const val NAME = "ObservableUnion"
    }

    override fun createSecretary(): ISecretary<IObservableSubscriber> {
        return Secretary()
    }

    override fun getName(): String {
        return NAME
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is IObservableUnion) 0 else 1
    }

    override fun getObservables(): List<IObservable> {
        return secretary.values()
    }

    override fun register(subscriber: IObservableSubscriber): Boolean {
        super.register(subscriber)

        val list = subscriber.getObservable()
        for (observable in getObservables()) {
            val name = observable.getName()
            if (list.contains(name)) {
                observable.addObserver(subscriber)
            }
        }
        return true
    }

    override fun unregister(subscriber: IObservableSubscriber) {
        super.unregister(subscriber)

        val list = subscriber.getObservable()
        for (observable in getObservables()) {
            if (list.contains(observable.getName())) {
                observable.removeObserver(subscriber)
            }
        }
    }

    override fun onUnRegister() {
        for (observable in getObservables()) {
            observable.unregister()
        }
    }

    override fun onChange(name: String, obj: Any) {
        val observable = getObservable(name)
        observable?.onChange(obj)
    }

    override fun getObservable(name: String): IObservable? {
        return secretary.get(name)
    }

    override fun register(observable: IObservable): Boolean {
        secretary.put(observable.getName(), observable)
        return true
    }

    override fun unregister(observable: IObservable): Boolean {
        if (secretary.containsKey(observable.getName())) {
            if (observable == secretary.get(observable.getName())) {
                secretary.remove(observable.getName())
                return true
            }
        }
        return false
    }

}
