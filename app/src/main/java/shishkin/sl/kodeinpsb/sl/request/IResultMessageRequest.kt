package shishkin.sl.kodeinpsb.sl.request


interface IResultMessageRequest<T> : IRequest {
    /**
     * Получить имя собственника запроса
     *
     * @return имя собственника запроса
     */
    fun getOwnerName(): String

    /**
     * Получить список получателей запроса
     *
     * @return список получателей запроса
     */
    fun getCopyTo(): List<String>

    /**
     * Установить список получателей запроса
     *
     * @param copyTo список получателей запроса
     */
    fun setCopyTo(copyTo: List<String>)

    /**
     * Получить результат запроса
     *
     * @return результат запроса
     */
    fun getData(): T

    /**
     * Установить результат запроса
     *
     * @param data результат запроса
     */
    fun setData(data: T)

    /**
     * Получить ошибку запроса
     *
     * @return ошибка запроса
     */
    fun getError(): Error

    /**
     * Установить ошибку запроса
     *
     * @param error ошибка запроса
     */
    fun setError(error: Error)

    /**
     * Разослать результаты запроса
     */
    fun response()

}
