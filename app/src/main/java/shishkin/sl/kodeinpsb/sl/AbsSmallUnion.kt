package shishkin.sl.kodeinpsb.sl

import shishkin.sl.kodeinpsb.sl.state.IStateable
import shishkin.sl.kodeinpsb.sl.state.State


abstract class AbsSmallUnion<T : ISpecialistSubscriber> : AbsSpecialist(), ISmallUnion<T> {
    private val secretary = createSecretary()

    override fun createSecretary(): ISecretary<T> {
        return RefSecretary()
    }

    override fun checkSubscriber(subscriber: T): Boolean {
        return validate() && !subscriber.getName().isNullOrEmpty()
    }

    override fun register(subscriber: T): Boolean {
        if (!checkSubscriber(subscriber)) {
            //ErrorSpecialistImpl.getInstance()
            //    .onError(NAME, "Suscriber is not authenticated : $subscriber", true)
            return false
        }

        if (!subscriber.validate()) {
            //ErrorSpecialistImpl.getInstance().onError(
            //    NAME,
            //    "Registration not valid subscriber: $subscriber", true
            //)
            return false
        }

        val cnt = secretary.size()

        secretary.put(subscriber.getName(), subscriber)

        if (cnt == 0 && secretary.size() == 1) {
            onRegisterFirstSubscriber()
        }
        onAddSubscriber(subscriber)
        return true
    }

    override fun unregister(subscriber: T) {
        val cnt = secretary.size()
        if (secretary.containsKey(subscriber.getName())) {
            if (subscriber.equals(secretary.get(subscriber.getName()))) {
                secretary.remove(subscriber.getName())
            }
        }

        if (cnt == 1 && secretary.size() == 0) {
            onUnRegisterLastSubscriber()
        }
    }

    override fun unregister(name: String) {
        if (hasSubscriber(name)) {
            val subscriber = getSubscriber(name)
            if (subscriber != null) {
                unregister(subscriber)
            }
        }
    }

    override fun onAddSubscriber(subscriber: T) {}

    override fun onRegisterFirstSubscriber() {}

    override fun onUnRegisterLastSubscriber() {}

    override fun getSubscribers(): List<T> {
        return secretary.values()
    }

    override fun getValidatedSubscribers(): List<T> {
        val subscribers = ArrayList<T>()
        for (subscriber in getSubscribers()) {
            if (subscriber != null && subscriber.validate()) {
                subscribers.add(subscriber)
            }
        }
        return subscribers
    }

    override fun getReadySubscribers(): List<T> {
        val subscribers = ArrayList<T>()
        for (subscriber in getSubscribers()) {
            if (subscriber != null && subscriber.validate()) {
                if (subscriber is IStateable) {
                    val state = (subscriber as IStateable).getState()
                    if (state == State.STATE_READY) {
                        subscribers.add(subscriber)
                    }
                }
            }
        }
        return subscribers
    }

    override fun getSubscriber(name: String): T? {
        if (name.isNullOrEmpty()) return null

        return if (!secretary.containsKey(name)) {
            null
        } else secretary.get(name)
    }

    override fun hasSubscribers(): Boolean {
        return !secretary.isEmpty()
    }

    override fun hasSubscriber(name: String): Boolean {
        return secretary.containsKey(name)
    }

    override fun stop() {
        secretary.clear()
    }
}