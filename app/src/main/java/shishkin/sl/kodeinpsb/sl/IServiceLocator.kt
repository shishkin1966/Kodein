package shishkin.sl.kodeinpsb.sl

/**
 * Итерфейс администратора(Service Locator)
 */
interface IServiceLocator : ISubscriber {
    /**
     * Проверить существование специалиста
     *
     * @param name имя специалиста
     * @return true - специалист существует
     */
    fun exists(name: String): Boolean

    /**
     * Получить специалиста
     *
     * @param <C>  тип специалиста
     * @param name имя специалиста
     * @return специалист
    </ */
    fun <C:ISpecialist> get(name: String): C?

    /**
     * Зарегистрировать специалиста
     *
     * @param specialist специалист
     * @return флаг - операция завершена успешно
     */
    fun register(specialist: ISpecialist): Boolean

    /**
     * Отменить регистрацию специалиста
     *
     * @param name имя специалиста
     * @return флаг - операция завершена успешно
     */
    fun unregister(name: String): Boolean

    /**
     * Зарегистрировать подписчика специалиста
     *
     * @param subscriber подписчик специалиста
     * @return флаг - операция завершена успешно
     */
    fun register(subscriber: ISpecialistSubscriber): Boolean

    /**
     * Отменить регистрацию подписчика специалиста
     *
     * @param subscriber подписчик специалиста
     * @return флаг - операция завершена успешно
     */
    fun unregister(subscriber: ISpecialistSubscriber): Boolean

    /**
     * Установить подписчика текущим
     *
     * @param subscriber подписчик
     * @return флаг - операция завершена успешно
     */
    fun setCurrentSubscriber(subscriber: ISpecialistSubscriber): Boolean

    /**
     * Событие - остановка service locator
     */
    fun onStop()

    /**
     * Событие - старт service locator
     */
    fun onStart()

    /**
     * Получить список специалистов
     *
     * @return список специалистов
     */
    fun getSpecialists(): List<ISpecialist>
}
