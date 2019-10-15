package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объединения подписчиков
 *
 * @param <T> the type parameter
 */
interface IUnion<T : IProviderSubscriber> : ISmallUnion<T> {

    fun setCurrentSubscriber(subscriber: T): Boolean

    fun getCurrentSubscriber(): T?
}
