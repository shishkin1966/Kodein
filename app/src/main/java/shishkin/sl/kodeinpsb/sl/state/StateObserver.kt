package shishkin.sl.kodeinpsb.sl.state

import java.lang.ref.WeakReference

class StateObserver : IStateable {
    private var state = State.STATE_CREATE
    private var listener: WeakReference<IStateListener>? = null

    constructor(listener: IStateListener?) {
        if (listener != null) {
            this.listener = WeakReference(listener)
        }
        setState(State.STATE_CREATE)
    }

    /**
     * Получить состояние объекта
     *
     * @return состояние объекта
     */
    override fun getState(): Int {
        return state
    }

    /**
     * Установить состояние объекта
     *
     * @param state состояние объекта
     */
    override fun setState(state: Int) {
        this.state = state
        when (state) {
            State.STATE_CREATE -> onCreateView()

            State.STATE_READY -> onReadyView()

            State.STATE_DESTROY -> onDestroyView()

            else -> {
            }
        }
    }

    private fun onCreateView() {
        if (listener != null && listener!!.get() != null) {
            listener!!.get()!!.onCreateView()
        }
    }

    private fun onReadyView() {
        if (listener != null && listener!!.get() != null) {
            listener!!.get()!!.onReadyView()
        }
    }

    private fun onDestroyView() {
        if (listener != null && listener!!.get() != null) {
            listener!!.get()!!.onDestroyView()
        }
    }

}