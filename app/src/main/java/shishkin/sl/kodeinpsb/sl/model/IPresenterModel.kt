package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.presenter.IPresenter


interface IPresenterModel : IModel {
    /**
     * Установить презентер модели
     *
     * @param presenter презентер
     */
    fun setPresenter(presenter: IPresenter)

    /**
     * Получить презентер модели
     *
     * @return презентер
     */
    fun <C> getPresenter(): C

}
