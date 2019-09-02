package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IModel<M> : IValidated {
    /**
     * Получить View объект модели
     *
     * @return View объект модели
     */
    fun getView(): M?

    /**
     * Установить View объект модели
     *
     * @param view View объект модели
     */
    fun setView(view: M)

    /**
     * Добавить слушателя к модели
     *
     * @param stateable stateable объект
     */
    fun addStateObserver(stateable: IStateable)

    /**
     * Установить presenter модели
     *
     * @param presenter presenter модели
     */
    fun setPresenter(presenter: IPresenter)

    /**
     * Получить presenter модели
     *
     * @return презентер модели
     */
    fun <C> getPresenter(): C?

    /**
     * Установить State Observerable у объектов модели
     */
    fun addStateObserver()

}
