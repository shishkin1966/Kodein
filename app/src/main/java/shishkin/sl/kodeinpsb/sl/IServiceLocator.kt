package shishkin.sl.kodeinpsb.sl

import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber

/**
 * Итерфейс администратора(Service Locator)
 */
interface IServiceLocator : INamed {
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
    fun <C : ISpecialist> get(name: String): C?

    /**
     * Зарегистрировать специалиста
     *
     * @param specialist специалист
     * @return флаг - операция завершена успешно
     */
    fun register(specialist: ISpecialist): Boolean

    /**
     * Зарегистрировать специалиста
     *
     * @param specialist имя специалиста
     * @return флаг - операция завершена успешно
     */
    fun register(specialist: String): Boolean

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
     * Остановитить работу service locator
     */
    fun stop()

    /**
     * Запустить работу service locator
     */
    fun start()

    /**
     * Получить список специалистов
     *
     * @return список специалистов
     */
    fun getSpecialists(): List<ISpecialist>

    /**
     * Получить фабрику специалистов
     *
     * @return фабрика специалистов
     */
    fun getSpecialistFactory(): ISpecialistFactory

    /**
     * Зарегистрировать подписчика у IObservable специалиста
     *
     * @param subscriber подписчик IObservable специалиста
     * @return флаг - операция завершена успешно
     */
    fun register(subscriber: IObservableSubscriber): Boolean

    /**
     * Отменить регистрацию у IObservable специалиста
     *
     * @param subscriber подписчик IObservable специалиста
     * @return флаг - операция завершена успешно
     */
    fun unregister(subscriber: IObservableSubscriber): Boolean

}
