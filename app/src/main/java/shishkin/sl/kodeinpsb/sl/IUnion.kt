package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объединения подписчиков
 *
 * @param <T> the type parameter
 */
interface IUnion<T:ISpecialistSubscriber> : ISmallUnion<T> {

    fun setCurrentSubscriber(subscriber: ISpecialistSubscriber) : Boolean

}
