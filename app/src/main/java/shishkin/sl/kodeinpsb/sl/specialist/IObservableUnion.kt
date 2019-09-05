package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISmallUnion
import shishkin.sl.kodeinpsb.sl.observe.IObservable

interface IObservableUnion<T:IObservableSubscriber> : ISmallUnion<T> {
    /**
     * Зарегестрировать слушаемый объект
     *
     * @param observable слушаемый (IObservable) объект
     */
    fun register(observable: IObservable<T>)

     /**
     * Получить слушаемый объект
     *
     * @param name имя слушаемого (IObservable) объекта
     * @return слушаемый(IObservable) объект
     */
    operator fun get(name: String): IObservable<T>

    /**
     * Получить список слушаемых объектов
     *
     * @return список слушаемых(IObservable) объектов
     */
    fun getObservables(): List<IObservable<T>>

}