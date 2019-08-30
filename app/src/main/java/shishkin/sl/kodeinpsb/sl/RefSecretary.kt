package shishkin.sl.kodeinpsb.sl

import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList


class RefSecretary<T> : ISecretary<T> {
    private val subscribers =
        Collections.synchronizedMap(ConcurrentHashMap<String, WeakReference<T>>())

    override fun remove(key: String): T? {
        if (key.isNullOrEmpty()) return null

        checkNull()
        return subscribers.remove(key)?.get()
    }

    override fun size(): Int {
        checkNull()
        return subscribers.size
    }

    override fun put(key: String, value: T?): T? {
        if (value == null) return null
        if (key.isNullOrEmpty()) return null

        subscribers.put(key, WeakReference(value))
        return get(key)
    }

    override fun containsKey(key: String): Boolean {
        if (key.isNullOrEmpty()) return false

        checkNull()
        return subscribers.containsKey(key)
    }

    override operator fun get(key: String): T? {
        if (key.isNullOrEmpty()) return null

        checkNull()
        return subscribers.get(key)?.get()
    }

    override fun values(): List<T> {
        checkNull()
        val list = ArrayList<T>()
        for (reference in subscribers.values) {
            if (reference != null) {
                val obj = reference.get()
                if (obj != null) {
                    list.add(obj)
                }
            }
        }
        return list
    }

    override fun isEmpty(): Boolean {
        checkNull()
        return subscribers.isEmpty()
    }

    private fun checkNull() {
        for (entry in subscribers.entries) {
            if (entry.value == null || entry.value.get() == null) {
                subscribers.remove(entry.key)
            }
        }
    }

    override fun clear() {
        subscribers.clear()
    }

    override fun keys(): Collection<String> {
        return subscribers.keys
    }

}