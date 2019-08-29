package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объекта - подписчика.
 */
interface ISubscriber {
    /**
     * Получить имя подписчика
     *
     * @return имя подписчика
     */
    fun getName() : String

}