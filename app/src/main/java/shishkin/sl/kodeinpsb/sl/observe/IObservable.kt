package shishkin.sl.kodeinpsb.sl.observe

import shishkin.sl.kodeinpsb.sl.ISubscriber
import shishkin.sl.kodeinpsb.sl.specialist.IObservableSubscriber
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber


interface IObservable<T:IObservableSubscriber> : ISpecialistSubscriber {
    /**
     * Добавить слушателя к слушаемому объекту
     *
     * @param subscriber слушатель
     */
    fun addObserver(subscriber: T)

    /**
     * Удалить слушателя у слушаемого объекта
     *
     * @param subscriber слушатель
     */
    fun removeObserver(subscriber: T)

    /**
     * Зарегестрировать слушаемый объект. Вызывается при появлении
     * первого слушателя
     */
    fun register()

    /**
     * Отменить регистрацию слушаемого объекта. Вызывается при удалении
     * последнего слушателя
     */
    fun unregister()

    /**
     * Событие - в слушаемом объекте произошли изменения
     *
     * @param object объект изменения
     */
    fun onChange(obj: IObservable<T>)

    /**
     * Получить список слушателей
     *
     * @return список слушателей
     */
    fun getObserver(): List<T>

}