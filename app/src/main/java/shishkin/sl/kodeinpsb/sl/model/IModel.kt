package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IModel : IValidated {
    /**
     * Получить View объект модели
     *
     * @return View объект модели
     */
    fun <M : IModelView> getView(): M

    /**
     * Добавить слушателя к модели
     *
     * @param stateable stateable объект
     */
    fun addStateObserver(stateable: IStateable)

}
