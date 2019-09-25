package shishkin.sl.kodeinpsb.sl.state

import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList


class StateObservable(state: Int) : IStateable {
    private val list = Collections.synchronizedList(ArrayList<WeakReference<IStateable>>())
    private var _state = State.STATE_CREATE

    init {
        setState(state)
    }

    override fun setState(state: Int) {
        _state = state
        for (stateable in list) {
            if (stateable?.get() != null) {
                stateable.get()!!.setState(_state)
            }
        }
    }

    override fun getState(): Int {
        return _state
    }

    /**
     * Добавить слушателя состояний
     *
     * @param stateable слушатель состояний
     */
    fun addObserver(stateable: IStateable?) {
        if (stateable != null) {
            for (ref in list) {
                if (ref?.get() != null && ref.get() === stateable) {
                    return
                }
            }

            stateable.setState(_state)
            list.add(WeakReference(stateable))
        }
    }

    /**
     * Удалить слушателя состояний
     *
     * @param stateable слушатель состояний
     */
    fun removeObserver(stateable: IStateable) {
        for (ref in list) {
            if (ref?.get() != null && ref.get() === stateable) {
                list.remove(ref)
                return
            }
        }
    }

    /**
     * Удалить всех слушателей
     */
    fun clear() {
        list.clear()
    }


}
