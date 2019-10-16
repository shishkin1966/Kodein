package shishkin.sl.kotlin.sl.observe

import shishkin.sl.kotlin.sl.provider.IObservableSubscriber

interface IObjectObservableSubscriber : IObservableSubscriber {
    /**
     * Получить список слушаемых объектов БД
     *
     * @return список слушаемых объектов БД
     */
    fun getListenObjects(): List<String>
}
