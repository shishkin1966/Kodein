package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объединения подписчиков
 *
 * @param <T> the type parameter
</T> */
interface IUnion<T> : ISmallUnion<T> {

    var currentSubscriber: T

}
