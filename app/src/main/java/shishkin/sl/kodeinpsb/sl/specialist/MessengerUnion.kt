package shishkin.sl.kodeinpsb.sl.specialist

import com.annimon.stream.function.Predicate
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.AbsSmallUnion
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.Secretary
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.state.State
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList


class MessengerUnion : AbsSmallUnion<IMessengerSubscriber>(), IMessengerUnion {
    companion object {
        const val NAME = "MessengerUnion"
    }

    private val messages = Collections.synchronizedMap(ConcurrentHashMap<Int, IMessage>())
    private val messagingList = Secretary<List<String>>()
    private val atomicId = AtomicInteger(0)

    override fun getName(): String {
        return NAME
    }

    override fun onAddSubscriber(subscriber: IMessengerSubscriber) {
        readMessages(subscriber)
    }

    override fun getMessage(subscriber: IMessengerSubscriber): List<IMessage> {
        if (messages.isEmpty()) {
            return ArrayList()
        }

        // удаляем старые письма
        val name = subscriber.getName()
        val currentTime = System.currentTimeMillis()
        val list = ApplicationUtils.filter(
            messages.values,
            Predicate { message -> message.contains(name) && message.getEndTime() != -1L && message.getEndTime() < currentTime })
            .toList()
        for (message in list) {
            messages.remove(message.getMessageId())
        }

        if (messages.isEmpty()) {
            return ArrayList()
        }

        val byId = Comparator<IMessage> { left, right ->
            left.getMessageId().compareTo(right.getMessageId())
        }
        return ApplicationUtils.filter(
            messages.values,
            Predicate { message -> message.contains(name) && (message.getEndTime() == -1L || message.getEndTime() != -1L && message.getEndTime() > currentTime) })
            .sorted(byId).toList()
    }

    override fun clearMessages(name: String) {
        if (messages.isEmpty()) {
            return
        }

        val list = ApplicationUtils.filter(
            messages.values,
            Predicate { message -> message.contains(name) }).toList()
        if (!list.isEmpty()) {
            for (message in list) {
                messages.remove(message.getMessageId())
            }
        }
    }

    override fun clearMessages() {
        messages.clear();
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is IMessengerUnion) 0 else 1
    }

    override fun addMessage(message: IMessage) {
        val list = ArrayList<String>()
        list.addAll(message.getCopyTo())
        if (!message.getAddress().isNullOrBlank()) {
            list.add(message.getAddress()!!)
        }
        val addresses = ArrayList<String>()
        for (address in list) {
            addresses.addAll(getAddresses(address))
        }
        for (address in addresses) {
            val id = atomicId.incrementAndGet()
            val newMessage = message.copy()
            newMessage.setMessageId(id)
            newMessage.setAddress(address)
            newMessage.setCopyTo(ArrayList())

            if (!message.isCheckDublicate()) {
                messages.put(id, newMessage)
            } else {
                removeDublicate(newMessage)
                messages.put(id, newMessage)
            }

            checkAndReadMessagesSubscriber(address)
        }
    }

    private fun checkAndReadMessagesSubscriber(address: String) {
        val subscriber = getSubscriber(address)
        if (subscriber != null) {
            if (address.equals(subscriber.getName())) {
                val state = subscriber.getState()
                if (state == State.STATE_READY) {
                    readMessages(subscriber)
                }
            }
        }
    }

    private fun checkSubscriber(address: String): IMessengerSubscriber? {
        val subscriber = getSubscriber(address)
        if (subscriber != null) {
            if (address.equals(subscriber.getName())) {
                val state = subscriber.getState()
                if (state == State.STATE_READY || state == State.STATE_NOT_READY) {
                    return subscriber
                }
            }
        }
        return null
    }

    private fun getAddresses(address: String): List<String> {
        val addresses = ArrayList<String>()
        if (messagingList.containsKey(address)) {
            val list = messagingList.get(address)
            if (list != null) {
                for (adr in list) {
                    addresses.addAll(getAddresses(adr))
                }
            }
        } else {
            addresses.add(address)
        }
        return addresses
    }

    private fun removeDublicate(message: IMessage) {
        for (tmpMessage in messages.values) {
            if (message.getName().equals(tmpMessage.getName()) && message.getAddress().equals(
                    tmpMessage.getAddress()
                )
            ) {
                removeMessage(tmpMessage)
            }
        }
    }

    override fun addNotMandatoryMessage(message: IMessage) {
        val list = ArrayList<String>()
        list.addAll(message.getCopyTo())
        if (!message.getAddress().isNullOrBlank()) {
            list.add(message.getAddress()!!)
        }
        val addresses = ArrayList<String>()
        for (address in list) {
            addresses.addAll(getAddresses(address))
        }
        for (address in addresses) {
            val subscriber = checkSubscriber(address)
            if (subscriber != null) {
                message.read(subscriber)
            }
        }
    }

    override fun replaceMessage(message: IMessage) {
        val list = ArrayList<String>()
        list.addAll(message.getCopyTo())
        if (!message.getAddress().isNullOrBlank()) {
            list.add(message.getAddress()!!)
        }
        val addresses = ArrayList<String>()
        for (address in list) {
            addresses.addAll(getAddresses(address))
        }
        for (address in addresses) {
            val id = atomicId.incrementAndGet()
            val newMessage = message.copy()
            newMessage.setMessageId(id)
            newMessage.setAddress(address)
            newMessage.setCopyTo(ArrayList())

            removeDublicate(newMessage)
            messages.put(id, newMessage)

            checkAndReadMessagesSubscriber(address);
        }
    }

    override fun removeMessage(message: IMessage) {
        messages.remove(message.getMessageId());
    }

    override fun readMessages(subscriber: IMessengerSubscriber) {
        val list = getMessage(subscriber)
        for (message in list) {
            val state = subscriber.getState()
            if (state == State.STATE_READY) {
                message.read(subscriber)
                removeMessage(message)
            }
        }
    }

    override fun addMessagingList(name: String, addresses: List<String>) {
        messagingList.put(name, addresses);
    }

    override fun addMessagingList(name: String, addresses: Array<String>) {
        messagingList.put(name, addresses.toList());
    }

    override fun removeMessagingList(name: String) {
        messagingList.remove(name);
    }

    override fun getMessagingList(name: String): List<String>? {
        if (messagingList.containsKey(name)) {
            return messagingList.get(name)
        } else {
            return null
        }
    }

}
