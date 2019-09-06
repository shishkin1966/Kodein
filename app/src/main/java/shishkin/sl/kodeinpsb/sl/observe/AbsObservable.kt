package shishkin.sl.kodeinpsb.sl.observe

import shishkin.sl.kodeinpsb.sl.Secretary
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber


abstract class AbsObservable<T : IObservableSubscriber> : IObservable<T> {

    private val secretary = Secretary<T>()

    override fun addObserver(subscriber: T) {
        secretary.put(subscriber.getName(), subscriber);

        if (secretary.size() == 1) {
            register();
        }
    }

    override fun removeObserver(subscriber: T) {
        if (secretary.containsKey(subscriber.getName())) {
            if (subscriber.equals(secretary.get(subscriber.getName()))) {
                secretary.remove(subscriber.getName())
            }

            if (secretary.isEmpty()) {
                unregister()
            }
        }
    }

    override fun onChange(obj: Any) {
        for (observableSubscriber in secretary.values()) {
            if (observableSubscriber.validate()) {
                observableSubscriber.onChange(obj)
            }
        }
    }

    override fun getObserver(): List<T> {
        return secretary.values()
    }
}
