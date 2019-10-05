package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объекта, который регистрируется у специалистов для получения/предоставления сервиса
 */
interface ISpecialistSubscriber : ISubscriber {

    /**
     * Получить список имен специалистов, у которых должен быть зарегистрирован объект
     *
     * @return список имен специалистов
     */
    fun getSpecialistSubscription(): List<String>

    /**
     * Событие - специалист прекратил работу
     */
    fun onStopSpecialist(specialist: ISpecialist)
}
