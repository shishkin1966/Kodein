package shishkin.sl.kotlin.sl.presenter

import shishkin.sl.kotlin.sl.action.IActionHandler
import shishkin.sl.kotlin.sl.action.IActionListener
import shishkin.sl.kotlin.sl.provider.IMessengerSubscriber
import shishkin.sl.kotlin.sl.state.IStateListener


interface IPresenter : IStateListener, IActionListener, IActionHandler,
    IMessengerSubscriber {
    /**
     * Флаг - регистрировать презентер в объединении презентеров
     *
     * @return true - регистрировать (презентер - глобальный)
     */
    fun isRegister(): Boolean

}
