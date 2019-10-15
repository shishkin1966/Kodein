package shishkin.sl.kodeinpsb.app.provider

import android.location.Location
import shishkin.sl.kodeinpsb.sl.IProviderSubscriber

interface ILocationSubscriber : IProviderSubscriber {

    /**
     * Установить у подписчика текущее местоположение
     *
     * @param location текущий Location
     */
    fun setLocation(location: Location)

}
