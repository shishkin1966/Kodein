package shishkin.sl.kotlin.sl.action

interface IActionListener {
    /**
     * Добавить новое действие к исполнению
     *
     * @param action событие
     */
    fun addAction(action: IAction)
}
