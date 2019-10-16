package shishkin.sl.kotlin.sl.request

import shishkin.sl.kotlin.sl.INamed
import shishkin.sl.kotlin.sl.IValidated
import shishkin.sl.kotlin.sl.data.ExtResult

interface IResponseListener : IValidated, INamed {

    /**
     * Событие - пришел ответ с результатами запроса
     *
     * @param result - результат
     */
    fun response(result: ExtResult)

}
