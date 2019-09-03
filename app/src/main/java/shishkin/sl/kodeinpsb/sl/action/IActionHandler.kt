package shishkin.sl.kodeinpsb.sl.action

interface IActionHandler {
    /**
     * Обработать событие
     *
     * @param action  событие
     */
    fun onAction(action: IAction): Boolean

}
