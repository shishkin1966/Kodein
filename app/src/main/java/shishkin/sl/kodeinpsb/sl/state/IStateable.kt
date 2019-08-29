package shishkin.sl.kodeinpsb.sl.state

interface IStateable {
    /**
     * Получить состояние объекта
     *
     * @return the state
     */
    fun getState(): Int

    /**
     * Установить состояние объекта
     *
     * @param state the state
     */
    fun setState(state: Int)

}