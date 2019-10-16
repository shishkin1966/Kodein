package shishkin.sl.kotlin.sl.presenter

import shishkin.sl.kotlin.sl.model.IModel
import shishkin.sl.kotlin.sl.model.IModelView


interface IModelPresenter : IPresenter {
    /**
     * Получить модель презентера
     *
     * @return the model
     */
    fun <M : IModel> getModel(): M

    /**
     * Получить View модели
     *
     * @return the view model
     */
    fun <V : IModelView> getView(): V

}
