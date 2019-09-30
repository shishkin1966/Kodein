package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.sl.INamed
import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.data.ExtResult

interface IResponseListener : IValidated, INamed {

    /**
     * Событие - пришел ответ с результатами запроса
     *
     * @param result - результат
     */
    fun response(result: ExtResult)

}
