package shishkin.sl.kodeinpsb.sl

import shishkin.sl.kodeinpsb.sl.specialist.ActivityUnion
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton


/**
 * Абстрактный администратор
 */
@Suppress("UNCHECKED_CAST")
abstract class AbsServiceLocator : IServiceLocator {
    private val secretary = Secretary<ISpecialist>()

    companion object {
        const val NAME = "AbsServiceLocator"
    }

    override fun <C : ISpecialist> get(name: String): C? {
        if (!exists(name)) {
            if (!registerSpecialist(name)) {
                return null;
            }
        }

        if (secretary.get(name) != null) {
            return secretary.get(name) as C
        } else {
            secretary.remove(name)
        }
        return null
    }

    override fun exists(name: String): Boolean {
        return secretary.containsKey(name)
    }

    override fun registerSpecialist(specialist: ISpecialist): Boolean {
        if (secretary.containsKey(specialist.getName())) {
            val oldSpecialist = get<ISpecialist>(specialist.getName())
            if (oldSpecialist != null && oldSpecialist.compareTo(specialist) != 0) {
                return false
            }
            if (!unregisterSpecialist(specialist.getName())) {
                return false
            }
        }

        secretary.put(specialist.getName(), specialist)
        specialist.onRegister()
        return true
    }

    override fun registerSpecialist(name: String): Boolean {
        val specialist = getSpecialistFactory().create(name)
        return if (specialist != null) {
            registerSpecialist(specialist)
        } else false
    }

    override fun unregisterSpecialist(name: String): Boolean {
        if (secretary.containsKey(name)) {
            val specialist = secretary.get(name)
            if (specialist != null) {
                // нельзя отменить регистрацию у объединения с подписчиками
                if (!specialist.isPersistent()) {
                    if (specialist is ISmallUnion<*>) {
                        if (specialist.hasSubscribers()) {
                            return false
                        }
                    }
                    specialist.onUnRegister()
                    secretary.remove(name)
                }
            } else {
                secretary.remove(name)
            }
        }
        return true
    }

    override fun registerSpecialistSubscriber(subscriber: ISpecialistSubscriber): Boolean {
        val types = subscriber.getSpecialistSubscription()
        // регистрируемся subscriber у специалистов
        for (type in types) {
            if (secretary.containsKey(type)) {
                val specialist = secretary.get(type)
                if (specialist is ISmallUnion<*>) {
                    (specialist as ISmallUnion<ISpecialistSubscriber>).register(subscriber)
                }
            } else {
                registerSpecialist(type)
                if (secretary.containsKey(type)) {
                    (secretary.get(type) as ISmallUnion<ISpecialistSubscriber>).register(subscriber)
                } else {
                    ErrorSpecialistSingleton.instance
                        .onError(NAME, "Not found subscriber type: $type", false)
                    return false
                }

            }
        }
        return true
    }

    override fun unregisterSpecialistSubscriber(subscriber: ISpecialistSubscriber): Boolean {
        val types = subscriber.getSpecialistSubscription()
        for (type in types) {
            if (secretary.containsKey(type)) {
                val specialist = secretary.get(type)
                if (specialist is ISmallUnion<*>) {
                    (specialist as ISmallUnion<ISpecialistSubscriber>).unregister(subscriber)
                }
            }
        }
        return true
    }

    override fun setCurrentSubscriber(subscriber: ISpecialistSubscriber): Boolean {
        val types = subscriber.getSpecialistSubscription()
        for (type in types) {
            if (secretary.containsKey(type)) {
                val specialist = secretary.get(type)
                if (specialist is IUnion<*>) {
                    (specialist as IUnion<ISpecialistSubscriber>).setCurrentSubscriber(subscriber)
                }
            }
        }
        return true
    }

    override fun getSpecialists(): List<ISpecialist> {
        return secretary.values()
    }

    override fun stop() {
        if (ApplicationSpecialist.instance.isExit()) {
            val union = get<ActivityUnion>(ActivityUnion.NAME)
            if (union != null) {
                union.stop()
            }
        }
    }

}
