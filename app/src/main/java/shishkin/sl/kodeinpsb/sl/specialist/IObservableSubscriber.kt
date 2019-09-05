package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber
import shishkin.sl.kodeinpsb.sl.ISubscriber
import shishkin.sl.kodeinpsb.sl.observe.IObservable
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IObservableSubscriber : ISpecialistSubscriber, IStateable, ISubscriber {
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