package shishkin.sl.kodeinpsb.app.provider

import android.location.Address
import android.location.Location
import shishkin.sl.kodeinpsb.sl.ISmallUnion

interface ILocationUnion : ISmallUnion<ILocationSubscriber> {
    /**
     * запустить службу геолокации
     */
    fun startLocation()

    /**
     * остановить службу геолокации
     */
    fun stopLocation()

    /**
     * Получить текущее положение
     *
     * @return текущее положение
     */
    fun getLocation(): Location?

    /**
     * Получить список адресов по его месту
     *
     * @param location     location
     * @param countAddress кол-во адресов
     * @return список адресов
     */
    fun getAddress(location: Location, countAddress: Int): List<Address>

    /**
     * Получить список адресов по его месту
     *
     * @param location location
     * @return список адресов
     */
    fun getAddress(location: Location): List<Address>

    /**
     * Признак - модуль получает данные от сервиса определения местоположения
     */
    fun isGetLocation(): Boolean

    /**
     * Объединение определения местоположения запущено
     */
    fun isRuning(): Boolean
}
