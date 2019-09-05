package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISmallUnion
import shishkin.sl.kodeinpsb.sl.observe.IObservable


interface IObservableUnion : ISmallUnion<IObservable<IObservableSubscriber>> {
    /**
     * Получить список слушаемых объектов
     *
     * @return список слушаемых(IObservable) объектов
     */
    fun getObservables(): List<IObservable<IObservableSubscriber>>

    /**
     * Зарегестрировать слушателя слушаемего объекта
     *
     * @param subscriber слушатель
     */
    fun register(subscriber: IObservableSubscriber): Boolean

    /**
     * Отменить регистрацию слушателя слушаемего объекта
     *
     * @param subscriber слушатель
     */
    fun unregister(subscriber: IObservableSubscriber) : Boolean

}