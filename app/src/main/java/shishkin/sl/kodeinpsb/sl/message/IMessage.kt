package shishkin.sl.kodeinpsb.sl.message

import shishkin.sl.kodeinpsb.sl.INamed
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerSubscriber

interface IMessage : INamed, IAction {
    /**
     * Получить id действия
     *
     * @return id действия
     */
    fun getMessageId(): Int

    /**
     * Установить id действия
     *
     * @param id id действия
     * @return действие
     */
    fun setMessageId(id: Int): IMessage

    /**
     * Прочитать письмо.
     *
     * @param subscriber подписчик получения почты
     */
    fun read(subscriber: IMessengerSubscriber)

    /**
     * Проверить наличие адреса. Проверяются поля Получатель и CopyTo
     *
     * @param address адрес
     * @return true если адрес найден
     */
    operator fun contains(address: String): Boolean

    /**
     * Скопировать письмо
     *
     * @return письмо
     */
    fun copy(): IMessage

    /**
     * Получить поле CopyTo
     *
     * @return поле CopyTo
     */
    fun getCopyTo(): List<String>

    /**
     * Установить поле CopyTo
     *
     * @param copyTo список адресов
     * @return письмо
     */
    fun setCopyTo(copyTo: List<String>): IMessage

    /**
     * Получить адрес получателя
     *
     * @return адрес
     */
    fun getAddress(): String?

    /**
     * Установить адрес получателя
     *
     * @param address адрес получателя
     * @return письмо
     */
    fun setAddress(address: String): IMessage

    /**
     * Флаг - контролировать сервером наличие копий письма.
     * При добавлении письма все старые копии стираются
     *
     * @return true - контролировать на дубликаты
     */
    fun isCheckDublicate(): Boolean

    /**
     * Флаг - при удалении подписчика сообщение с сервера не удаляется
     *
     * @return true - при удалении подписчика сообщение с сервера не удаляется
     */
    fun isSticky(): Boolean

    /**
     * Получить время окончания жизни письма
     *
     * @return время окончания жизни письма
     */
    fun getEndTime(): Long

    /**
     * Установить время окончания жизни письма. При чтении почты с сервера,
     * просроченные сообщения удаляются
     *
     * @param keepAliveTime время окончания жизни письма
     * @return письмо
     */
    fun setEndTime(keepAliveTime: Long): IMessage


}
