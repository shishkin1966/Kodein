package shishkin.sl.kodeinpsb.sl.presenter

import shishkin.sl.kodeinpsb.sl.action.IActionHandler
import shishkin.sl.kodeinpsb.sl.action.IActionListener
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerSubscriber
import shishkin.sl.kodeinpsb.sl.state.IStateListener


interface IPresenter : IStateListener, IActionListener, IActionHandler, IMessengerSubscriber {
    /**
     * Флаг - регистрировать презентер в объединении презентеров
     *
     * @return true - регистрировать (презентер - глобальный)
     */
    fun isRegister(): Boolean

}
