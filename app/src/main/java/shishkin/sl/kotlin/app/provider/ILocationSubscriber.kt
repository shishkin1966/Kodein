package shishkin.sl.kotlin.app.provider

import android.location.Location
import shishkin.sl.kotlin.sl.IProviderSubscriber

interface ILocationSubscriber : IProviderSubscriber {

    /**
     * Установить у подписчика текущее местоположение
     *
     * @param location текущий Location
     */
    fun setLocation(location: Location)

}
