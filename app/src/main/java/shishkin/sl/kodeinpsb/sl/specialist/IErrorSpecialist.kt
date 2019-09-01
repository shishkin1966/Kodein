package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.data.Error

/**
 * Интерфейс специалиста обработки ошибок
 */
interface IErrorSpecialist : ISpecialist {
    /**
     * Событие - ошибка
     *
     * @param source источник ошибки
     * @param e      Exception
     */
    fun onError(source: String, e: Exception)

    /**
     * Событие - ошибка
     *
     * @param source    источник ошибки
     * @param throwable Throwable
     */
    fun onError(source: String, throwable: Throwable)

    /**
     * Событие - ошибка
     *
     * @param source         источник ошибки
     * @param e              Exception
     * @param displayMessage текст ошибки пользователю
     */
    fun onError(source: String, e: Exception, displayMessage: String?)

    /**
     * Событие - ошибка
     *
     * @param source    источник ошибки
     * @param message   текст ошибки пользователю
     * @param isDisplay true - отображать на сообщение на дисплее, false - сохранять в журнале
     */
    fun onError(source: String, message: String?, isDisplay: Boolean)

    /**
     * Событие - ошибка
     *
     * @param extError ошибка
     */
    fun onError(error: Error)

    /**
     * Получить путь к файлу лога ошибок
     *
     * @return путь к файлу лога ошибок
     */
    fun getPath(): String

}
