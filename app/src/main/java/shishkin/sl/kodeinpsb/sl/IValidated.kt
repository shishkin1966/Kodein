package shishkin.sl.kodeinpsb.sl

/**
 * Интерфейс объекта, который может быть проверен на жизнеспособность
 */
interface IValidated {
    /**
     * Проверить работоспособность объекта
     *
     * @return true - объект может обеспечивать свою функциональность
     */
    fun validate(): Boolean
}
