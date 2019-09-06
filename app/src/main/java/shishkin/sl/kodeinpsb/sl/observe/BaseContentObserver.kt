package shishkin.sl.kodeinpsb.sl.observe

import android.database.ContentObserver
import android.os.Handler
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber
import shishkin.sl.kodeinpsb.sl.observe.BaseContentObserver



class BaseContentObserver<T : IObservableSubscriber>() : ContentObserver(Handler())  {

    private var observable: IObservable<T>? = null

    constructor(observable : IObservable<T>) :this() {
        this.observable = observable
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)

        observable?.onChange(Boolean)
    }

}
