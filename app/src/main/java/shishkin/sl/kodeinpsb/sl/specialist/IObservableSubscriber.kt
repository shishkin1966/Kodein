package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber
import shishkin.sl.kodeinpsb.sl.IValidated
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IObservableSubscriber : IStateable, ISpecialistSubscriber, IValidated {
    /**
     * Получить список имен слушаемых объектов
     *
     * @return список имен слушаемых объектов
     */
    fun getObservable(): List<String>

    /**
     * Событие - объект изменен
     *
     * @param name имя слушаемого объекта
     * @param obj значение слушаемого объекта
     */
    fun onChange(name: String, obj: Any)

}
