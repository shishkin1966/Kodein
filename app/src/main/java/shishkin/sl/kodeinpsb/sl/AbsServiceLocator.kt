package shishkin.sl.kodeinpsb.sl

import shishkin.sl.kodeinpsb.sl.specialist.ErrorSpecialistSingleton

/**
 * Абстрактный администратор
 */
class AbsServiceLocator : IServiceLocator {
    private val secretary = Secretary<ISpecialist>()

    companion object {
        const val NAME = "AbsServiceLocator"
    }

    override fun <C : ISpecialist> get(name: String): C? {
        if (!exists(name)) {
            if (!register(name)) {
                return null
            }
        }

        try {
            if (secretary.get(name) != null) {
                return secretary.get(name) as C
            } else {
                secretary.remove(name)
            }
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        }

        return null
    }

    override fun exists(name: String): Boolean {
        return if (name.isNullOrEmpty()) {
            false
        } else secretary.containsKey(name)

    }

    override fun register(specialist: ISpecialist): Boolean {
        if (!specialist.getName().isNullOrEmpty()) {
            if (secretary.containsKey(specialist.getName())) {
                val oldSpecialist = get<ISpecialist>(specialist.getName())
                if (oldSpecialist != null && oldSpecialist.compareTo(specialist) !== 0) {
                    return false
                }
                if (!unregister(specialist)) {
                    return false
                }
            }

            try {
                // регистрируем специалиста у других специалистов
                if (specialist is ISpecialistSubscriber) {
                    val types = (specialist as ISpecialistSubscriber).getSpecialistSubscription()
                    for (type in types) {
                        if (secretary.containsKey(type)) {
                            (secretary.get(type) as ISmallUnion<ISpecialistSubscriber>).register(
                                specialist
                            )
                        }
                    }
                }

                // регистрируем других специалистов у специалиста
                if (specialist is ISmallUnion<*>) {


                }
                secretary.put(specialist.getName(), specialist)
                specialist.onRegister()
            } catch (e: Exception) {
                ErrorSpecialistSingleton.instance.onError(NAME, e)
                return false
            }
        }
        return true
    }

    override fun unregister(specialist: ISpecialist): Boolean {
        val name = specialist.getName()
        try {
            val specialistName = getShortName(name)
            if (secretary.containsKey(specialistName)) {
                val specialist = secretary.get(specialistName)
                if (specialist != null) {
                    if (!specialist!!.isPersistent()) {
                        // нельзя отменить подписку у объединения с подписчиками
                        if (SmallUnion::class.java!!.isInstance(specialist)) {
                            if ((specialist as SmallUnion).hasSubscribers()) {
                                return false
                            }
                        }

                        specialist!!.onUnRegister()

                        // отменяем регистрацию у других специалистов
                        if (SpecialistSubscriber::class.java!!.isInstance(specialist)) {
                            val subscribers =
                                (specialist as SpecialistSubscriber).getSpecialistSubscription()
                            for (subscriber in subscribers) {
                                val specialistSubscriber =
                                    secretary.get(getShortName(subscriber))
                                if (specialistSubscriber != null && SmallUnion::class.java!!.isInstance(
                                        specialistSubscriber
                                    )
                                ) {
                                    (specialistSubscriber as SmallUnion).unregister(specialist)
                                }
                            }
                        }
                        secretary.remove(specialistName)
                    }
                } else {
                    secretary.remove(specialistName)
                }
            }
        } catch (e: Exception) {
            ErrorSpecialistImpl.getInstance().onError(NAME, e)
            return false
        }

        return true
    }

    override fun register(subscriber: ISpecialistSubscriber): Boolean {
        if (subscriber != null && !StringUtils.isNullOrEmpty(subscriber!!.getName())) {
            try {
                val types = subscriber!!.getSpecialistSubscription()
                if (types != null) {
                    // регистрируемся subscriber у специалистов
                    for (subscriberType in types!!) {
                        if (!StringUtils.isNullOrEmpty(subscriberType)) {
                            val specialistName = getShortName(subscriberType)
                            if (!StringUtils.isNullOrEmpty(specialistName)) {
                                if (secretary.containsKey(specialistName)) {
                                    (secretary.get(specialistName) as SmallUnion).register(
                                        subscriber
                                    )
                                } else {
                                    register(subscriberType)
                                    if (secretary.containsKey(specialistName)) {
                                        (secretary.get(specialistName) as SmallUnion).register(
                                            subscriber
                                        )
                                    } else {
                                        ErrorSpecialistImpl.getInstance().onError(
                                            NAME,
                                            "Not found subscriber type: $subscriberType",
                                            false
                                        )
                                        return false
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                ErrorSpecialistImpl.getInstance().onError(NAME, e)
                return false
            }

        }
        return true
    }

    override fun unregister(subscriber: ISpecialistSubscriber): Boolean {
        if (subscriber != null) {
            try {
                val types = subscriber!!.getSpecialistSubscription()
                if (types != null) {
                    for (i in types!!.indices) {
                        types!!.set(i, getShortName(types!!.get(i)))
                    }

                    for (specialist in secretary.values()) {
                        if (SmallUnion::class.java!!.isInstance(specialist)) {
                            val subscriberType = getShortName(specialist.getName())
                            if (!StringUtils.isNullOrEmpty(subscriberType) && types!!.contains(
                                    subscriberType
                                )
                            ) {
                                (specialist as SmallUnion).unregister(subscriber)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                ErrorSpecialistImpl.getInstance().onError(NAME, e)
                return false
            }

        }
        return true
    }

    fun setCurrentSubscriber(subscriber: ISpecialistSubscriber): Boolean {
        try {
            if (subscriber != null) {
                val types = subscriber!!.getSpecialistSubscription()
                if (types != null) {
                    for (i in types!!.indices) {
                        types!!.set(i, getShortName(types!!.get(i)))
                    }

                    for (specialist in secretary.values()) {
                        if (Union::class.java!!.isInstance(specialist)) {
                            val specialistSubscriberType = getShortName(specialist.getName())
                            if (!StringUtils.isNullOrEmpty(specialistSubscriberType)) {
                                if (types!!.contains(specialistSubscriberType)) {
                                    (specialist as Union).setCurrentSubscriber(subscriber)
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            ErrorSpecialistImpl.getInstance().onError(NAME, e)
            return false
        }

        return true
    }

    override fun getSpecialists(): List<ISpecialist> {
        return secretary.values()
    }

}