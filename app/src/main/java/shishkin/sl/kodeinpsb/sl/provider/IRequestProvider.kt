package shishkin.sl.kodeinpsb.sl.provider

import shishkin.sl.kodeinpsb.sl.IProvider
import shishkin.sl.kodeinpsb.sl.request.IRequest

interface IRequestProvider : IProvider {
    /**
     * Выполнить запрос
     *
     * @param request запрос
     */
    fun request(request: IRequest)
}
