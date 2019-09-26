package shishkin.sl.kodeinpsb.app.specialist

import android.location.Location
import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber

interface ILocationSubscriber : ISpecialistSubscriber {

    /**
     * Установить у подписчика текущее местоположение
     *
     * @param location текущий Location
     */
    fun setLocation(location: Location)

}
