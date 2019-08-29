package shishkin.sl.kodeinpsb.sl


/**
 * Интерфейс малого объединения подписчиков
 */
interface ISmallUnion<T> : ISpecialist<T> {
    /**
     * Получить секретаря (объект учитывающий подписчиков)
     */
    fun createSecretary(): ISecretary<T>

    /**
     * Проверить подписчика
     *
     * @return результат проверки подписчика
     */
    fun checkSubscriber(subscriber: T): Boolean

    /**
     * Зарегестрировать подписчика
     *
     * @param subscriber подписчик
     */
    fun register(subscriber: T?): Boolean

    /**
     * Отключить подписчика
     *
     * @param subscriber подписчик
     */
    fun unregister(subscriber: T?)

    /**
     * Отключить подписчика по его имени
     *
     * @param name имя подписчика
     */
    fun unregister(name: String)

    /**
     * Получить список подписчиков
     *
     * @return список подписчиков
     */
    fun getSubscribers(): List<T>

    /**
     * Получить список валидных подписчиков
     *
     * @return список подписчиков
     */
    fun getValidatedSubscribers(): List<T>

    /**
     * Получить список готовых Stateable подписчиков
     *
     * @return список подписчиков
     */
    fun getReadySubscribers(): List<T>

    /**
     * Проверить наличие подписчиков
     *
     * @return true - подписчики есть
     */
    fun hasSubscribers(): Boolean

    /**
     * Проверить наличие подписчика
     *
     * @param name имя подписчика
     * @return true - подписчик есть
     */
    fun hasSubscriber(name: String): Boolean

    /**
     * Получить подписчика по его имени
     *
     * @param name имя подписчика
     * @return подписчик
     */
    fun getSubscriber(name: String): T?

    /**
     * Событие - появился первый подписчик
     */
    fun onRegisterFirstSubscriber()

    /**
     * Событие - отписан последний подписчик
     */
    fun onUnRegisterLastSubscriber()

    /**
     * Событие - добавлен подписчик
     *
     * @param subscriber подписчик
     */
    fun onAddSubscriber(subscriber: T)


}