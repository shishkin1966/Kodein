package shishkin.sl.kodeinpsb.sl.state

import java.lang.ref.WeakReference

class StateObserver : IStateable {
    private var _state = State.STATE_CREATE
    private var _listener: WeakReference<IStateListener>? = null

    constructor(listener: IStateListener?) {
        if (listener != null) {
            _listener = WeakReference(listener)
        }
        setState(State.STATE_CREATE)
    }

    /**
     * Получить состояние объекта
     *
     * @return состояние объекта
     */
    override fun getState(): Int {
        return _state
    }

    /**
     * Установить состояние объекта
     *
     * @param state состояние объекта
     */
    override fun setState(state: Int) {
        this._state = state
        when (state) {
            State.STATE_CREATE -> onCreateView()

            State.STATE_READY -> onReadyView()

            State.STATE_DESTROY -> onDestroyView()

            else -> {
            }
        }
    }

    private fun onCreateView() {
        if (_listener != null && _listener!!.get() != null) {
            _listener!!.get()!!.onCreateView()
        }
    }

    private fun onReadyView() {
        if (_listener != null && _listener!!.get() != null) {
            _listener!!.get()!!.onReadyView()
        }
    }

    private fun onDestroyView() {
        if (_listener != null && _listener!!.get() != null) {
            _listener!!.get()!!.onDestroyView()
        }
    }

}
