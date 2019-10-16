package shishkin.sl.kotlin.sl.model

import shishkin.sl.kotlin.sl.presenter.IModelPresenter


interface IPresenterModel : IModel {
    /**
     * Установить презентер модели
     *
     * @param presenter презентер
     */
    fun setPresenter(presenter: IModelPresenter)

    /**
     * Получить презентер модели
     *
     * @return презентер
     */
    fun <C> getPresenter(): C

}
