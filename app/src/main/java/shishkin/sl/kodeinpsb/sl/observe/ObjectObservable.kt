package shishkin.sl.kodeinpsb.sl.observe

import shishkin.sl.kodeinpsb.sl.INamed
import shishkin.sl.kodeinpsb.sl.Secretary
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber


class ObjectObservable : AbsObservable() {
    companion object {
        const val NAME = "ObjectObservable"
    }

    private val objects = Secretary<ArrayList<String>>()


    override fun getName(): String {
        return NAME
    }

    override fun addObserver(subscriber: IObservableSubscriber) {
        super.addObserver(subscriber)

        if (subscriber !is IObjectObservableSubscriber) return

        val listenObjects = subscriber.getListenObjects()
        for (listenObject in listenObjects) {
            if (!objects.containsKey(listenObject)) {
                objects.put(listenObject, ArrayList())
            }
            val list = objects.get(listenObject)
            if (list != null) {
                if (!list.contains(subscriber.getName())) {
                    list.add(subscriber.getName())
                }
            }
        }
    }

    override fun removeObserver(subscriber: IObservableSubscriber) {
        super.removeObserver(subscriber)

        if (subscriber !is IObjectObservableSubscriber) return

        for (observers in objects.values()) {
            if (observers.contains(subscriber.getName())) {
                observers.remove(subscriber.getName())
            }
        }
    }


    override fun onChange(obj: Any) {
        if (obj is INamed) {
            val observers = objects[obj.getName()]
            if (observers != null) {
                for (name in observers) {
                    val observer = getObserver(name)
                    observer?.onChange(getName(), obj)
                }
            }
        }
    }
}
