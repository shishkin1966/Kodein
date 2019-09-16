package shishkin.sl.kodeinpsb.sl.message

import shishkin.sl.kodeinpsb.sl.action.IAction
import java.util.*


abstract class AbsMessage() : IAction, IMessage {
    private var _id = -1
    private lateinit var _address: String
    private var _copyTo: LinkedList<String> = LinkedList()
    private var _keepAliveTime: Long = -1
    private var _subj: String? = null

    constructor(address: String) : this() {
        _address = address
    }

    constructor(message: IMessage) : this() {
        _address = message.getAddress()
        _copyTo.addAll(message.getCopyTo())
        setMessageId(message.getMessageId())
        _keepAliveTime = message.getEndTime()
    }

    override fun getMessageId(): Int {
        return _id
    }

    override fun setMessageId(id: Int): IMessage {
        _id = id
        return this
    }

    override fun getAddress(): String {
        return _address
    }

    override fun setAddress(address: String): IMessage {
        _address = address
        return this
    }

    override fun getCopyTo(): List<String> {
        return _copyTo
    }

    override fun setCopyTo(copyTo: List<String>): IMessage {
        _copyTo.clear()
        _copyTo.addAll(copyTo)
        return this
    }

    override fun contains(address: String): Boolean {
        if (address.isBlank()) {
            return false
        }

        if (address == _address) {
            return true
        }

        for (copyto in _copyTo) {
            if (copyto == address) {
                return true
            }
        }
        return false
    }

    override fun getEndTime(): Long {
        return _keepAliveTime
    }

    override fun setEndTime(keepAliveTime: Long): IMessage {
        _keepAliveTime = keepAliveTime
        return this
    }

    override fun isCheckDublicate(): Boolean {
        return false
    }

    override fun isSticky(): Boolean {
        return false
    }

    override fun setSubj(subj: String): IMessage {
        _subj = subj
        return this
    }

    override fun getSubj(): String {
        if (_subj.isNullOrEmpty()) {
            return this::class.java.simpleName
        } else {
            return _subj!!
        }
    }
}
