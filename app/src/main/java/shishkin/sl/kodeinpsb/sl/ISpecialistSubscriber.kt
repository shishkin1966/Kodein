package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объекта, который регистрируется у специалистов для получения/предоставления сервиса
 */
interface ISpecialistSubscriber : ISubscriber, IValidated {

    /**
     * Получить список имен специалистов, у которых должен быть зарегистрирован объект
     *
     * @return список имен специалистов
     */
    fun getSpecialistSubscription(): List<String>

}
