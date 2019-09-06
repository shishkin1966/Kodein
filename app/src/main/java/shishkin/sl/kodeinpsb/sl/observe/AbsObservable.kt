package shishkin.sl.kodeinpsb.sl.observe

import shishkin.sl.kodeinpsb.sl.Secretary
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber


abstract class AbsObservable : IObservable {

    private val secretary = Secretary<IObservableSubscriber>()

    override fun addObserver(subscriber: IObservableSubscriber) {
        secretary.put(subscriber.getName(), subscriber);

        if (secretary.size() == 1) {
            register();
        }
    }

    override fun removeObserver(subscriber: IObservableSubscriber) {
        if (secretary.containsKey(subscriber.getName())) {
            if (subscriber == secretary.get(subscriber.getName())) {
                secretary.remove(subscriber.getName())
            }

            if (secretary.isEmpty()) {
                unregister()
            }
        }
    }

    override fun onChange(observable: IObservable, obj: Any) {
        for (subscriber in secretary.values()) {
            if (subscriber.validate()) {
                subscriber.onChange(this, obj)
            }
        }
    }

    override fun getObserver(): List<IObservableSubscriber> {
        return secretary.values()
    }
}
