package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISubscriber
import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.observe.IObservable
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IObservableSubscriber : IStateable, ISubscriber, IValidated {
    /**
     * Получить список Observable объектов
     *
     * @return список имен Observable объектов
     */
    fun getObservable(): List<String>

    /**
     * Событие - объект изменен
     */
    fun onChange(obj: IObservable<*>)

}