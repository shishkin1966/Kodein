package shishkin.sl.kotlin.sl.model

import shishkin.sl.kotlin.sl.IValidated
import shishkin.sl.kotlin.sl.state.IStateable


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
