package shishkin.sl.kodeinpsb.sl.provider

import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.request.IRequest

interface IProvider : ISpecialist {
    /**
     * Выполнить запрос
     *
     * @param request запрос
     */
    fun request(request: IRequest)
}