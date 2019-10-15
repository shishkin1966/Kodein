package shishkin.sl.kodeinpsb.sl.provider

import android.content.Context
import shishkin.sl.kodeinpsb.sl.IProvider
import shishkin.sl.kodeinpsb.sl.data.ExtError

/**
 * Интерфейс провайдера обработки ошибок
 */
interface IErrorProvider : IProvider {
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
    fun onError(error: ExtError)

    /**
     * Получить путь к файлу лога ошибок
     *
     * @return путь к файлу лога ошибок
     */
    fun getPath(): String

    fun checkLog(context: Context)
}
