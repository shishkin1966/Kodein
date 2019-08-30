package shishkin.sl.kodeinpsb.sl.state

import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList


class StateObservable : IStateable {
    private val list = Collections.synchronizedList(ArrayList<WeakReference<IStateable>>())
    private var state = State.STATE_CREATE

    constructor (state: Int) {
        setState(state)
    }

    override fun setState(state: Int) {
        this.state = state
        for (stateable in list) {
            if (stateable != null && stateable!!.get() != null) {
                stateable!!.get()!!.setState(this.state)
            }
        }
    }

    override fun getState(): Int {
        return state
    }

    /**
     * Добавить слушателя состояний
     *
     * @param stateable слушатель состояний
     */
    fun addObserver(stateable: IStateable?) {
        if (stateable != null) {
            for (ref in list) {
                if (ref != null && ref!!.get() != null) {
                    if (ref!!.get() === stateable) {
                        return
                    }
                }
            }

            stateable!!.setState(state)
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
            if (ref != null && ref!!.get() != null) {
                if (ref!!.get() === stateable) {
                    list.remove(ref)
                    return
                }
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