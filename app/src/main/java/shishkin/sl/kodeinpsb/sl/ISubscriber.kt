package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объекта - подписчика у специалиста.
 */
interface ISubscriber : INamed {
    /**
     * Событие - специалист прекратил работу
     */
    fun onStopSpecialist(specialist: ISpecialist)
}
