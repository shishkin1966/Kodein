package shishkin.sl.kodeinpsb.sl.provider

import shishkin.sl.kodeinpsb.sl.IProvider

interface IApplicationProvider : IProvider {
    /**
     * Флаг - выход из приложения
     *
     * @return true = приложение остановлено
     */
    fun isExit(): Boolean

    /**
     * Отправить приложение в фон
     *
     */
    fun toBackground()
}
