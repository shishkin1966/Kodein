package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.sl.data.ExtError


interface IResultMessageRequest : IRequest {
    /**
     * Получить собственника запроса
     *
     * @return String - собственник запроса
     */
    fun getOwner(): String?

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
}
