package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISmallUnion
import shishkin.sl.kodeinpsb.sl.observe.IObservable
import shishkin.sl.kodeinpsb.sl.observe.ObjectObservable


interface IObservableUnion : ISmallUnion<IObservable> {
    /**
     * Получить список слушаемых объектов
     *
     * @return список слушаемых(IObservable) объектов
     */
    fun getObservables(): List<IObservable>

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
    fun unregister(subscriber: IObservableSubscriber): Boolean

    /**
     * Событие - изменился слушаемый объект
     *
     * @param name имя слушаемого объекта
     * @param obj новое значение
     */
    fun onChange(name: String, obj: Any)

}
