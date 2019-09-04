package shishkin.sl.kodeinpsb.sl.presenter

import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerSubscriber
import shishkin.sl.kodeinpsb.sl.state.IStateListener


interface IPresenter : IStateListener, IActionListener, IActionHandler, IMessengerSubscriber {
    /**
     * Получить модель презентера
     *
     * @return the model
     */
    fun <M : IModel> getModel(): M?

    /**
     * Получить View модели
     *
     * @return the view model
     */
    fun <V : IModelView> getView(): V?

    /**
     * Флаг - регистрировать презентер в объединении презентеров
     *
     * @return true - регистрировать (презентер - глобальный)
     */
    fun isRegister(): Boolean

    /**
     * Событие - презентер перешел в состояние STATE_RESUME
     */
    fun onStart()

    /**
     * Событие - презентер перешел в состояние STATE_DESTROY
     */
    fun onStop()
}
