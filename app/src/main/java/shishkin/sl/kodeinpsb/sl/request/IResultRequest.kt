package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.sl.data.ExtError


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
     * Получить ошибку запроса
     *
     * @return ошибка запроса
     */
    fun getError(): ExtError?

    /**
     * Установить ошибку запроса
     *
     * @param error ошибка запроса
     */
    fun setError(error: ExtError?)

    /**
     * Разослать результаты запроса
     */
    fun response()


}
