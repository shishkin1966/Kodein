package shishkin.sl.kodeinpsb.sl.presenter

import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerSubscriber
import shishkin.sl.kodeinpsb.sl.state.IStateListener


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
