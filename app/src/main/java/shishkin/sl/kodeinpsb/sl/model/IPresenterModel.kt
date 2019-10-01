package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.presenter.IModelPresenter


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
