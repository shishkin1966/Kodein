package shishkin.sl.kodeinpsb.sl.request

import shishkin.sl.kodeinpsb.sl.ISubscriber
import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.data.ExtResult

interface IResponseListener : IValidated, ISubscriber {

    /**
     * Событие - пришел ответ с результатами запроса
     *
     * @param result - результат
     */
    fun response(result: ExtResult)

}