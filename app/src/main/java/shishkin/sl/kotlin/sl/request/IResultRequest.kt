package shishkin.sl.kotlin.sl.request


interface IResultRequest : IRequest {
    /**
     * Получить собственника запроса
     *
     * @return IResponseListener - собственник запроса
     */
    fun getOwner(): IResponseListener?

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
     * Разослать результаты запроса
     */
    fun response()


}
