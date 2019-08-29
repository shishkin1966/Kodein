package shishkin.sl.kodeinpsb.sl

import shishkin.sl.kodeinpsb.common.isNullOrEmpty
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList


class Secretary<T>() : ISecretary<T> {
    private val subscribers = Collections.synchronizedMap(ConcurrentHashMap<String, T>())

    override fun remove(key: String): T? {
        return if (key.isNullOrEmpty(key)) null else subscribers.remove(key)
    }

    override fun size(): Int {
        return subscribers.size
    }

    override fun put(key: String, value: T?): T? {
        if (value == null) return null
        return if (key.isNullOrEmpty(key)) null else subscribers.put(key, value)
    }

    override fun containsKey(key: String): Boolean {
        return if (key.isNullOrEmpty(key)) false else subscribers.containsKey(key)
    }

    override operator fun get(key: String): T? {
        return if (key.isNullOrEmpty(key)) null else subscribers.get(key)
    }

    override fun values(): List<T> {
        return ArrayList(subscribers.values)
    }

    override fun isEmpty(): Boolean {
        return subscribers.isEmpty()
    }

    override fun clear() {
        subscribers.clear()
    }

    override fun keys(): Collection<String> {
        return subscribers.keys
    }


}