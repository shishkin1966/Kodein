package shishkin.sl.kotlin.sl.state

/**
 * Интерфейс слушателя View объекта, имеющего состояния
 */
interface IStateListener : IStateable {
    /**
     * Событие - view на этапе создания
     */
    fun onCreateView()

    /**
     * Событие - view готово к использованию
     */
    fun onReadyView()

    /**
     * Событие - уничтожение view
     */
    fun onDestroyView()

}
