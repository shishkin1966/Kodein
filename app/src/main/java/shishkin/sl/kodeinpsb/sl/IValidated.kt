package shishkin.sl.kodeinpsb.sl

import shishkin.sl.kodeinpsb.sl.data.Result

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