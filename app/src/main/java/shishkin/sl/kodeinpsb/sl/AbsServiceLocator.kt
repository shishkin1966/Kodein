package shishkin.sl.kodeinpsb.sl


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

    override fun register(specialist: ISpecialist): Boolean {
        if (secretary.containsKey(specialist.getName())) {
            val oldSpecialist = get<ISpecialist>(specialist.getName())
            if (oldSpecialist != null && oldSpecialist.compareTo(specialist) != 0) {
                return false
            }
            if (!unregister(specialist.getName())) {
                return false
            }
        }

        secretary.put(specialist.getName(), specialist)
        specialist.onRegister()
        return true
    }

    override fun unregister(name: String): Boolean {
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

    override fun register(subscriber: ISpecialistSubscriber): Boolean {
        val types = subscriber.getSpecialistSubscription()
        // регистрируемся subscriber у специалистов
        for (type in types) {
            if (secretary.containsKey(type)) {
                val specialist = secretary.get(type)
                if (specialist is ISmallUnion<*>) {
                    (specialist as ISmallUnion<ISpecialistSubscriber>).register(subscriber)
                }
            }
        }
        return true
    }

    override fun unregister(subscriber: ISpecialistSubscriber): Boolean {
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
                if (specialist is ISmallUnion<*>) {
                    (specialist as IUnion<ISpecialistSubscriber>).setCurrentSubscriber(subscriber)
                }
            }
        }
        return true
    }

    override fun getSpecialists(): List<ISpecialist> {
        return secretary.values()
    }

}
