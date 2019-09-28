package shishkin.sl.kodeinpsb.sl.specialist


import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.google.gson.Gson
import shishkin.sl.kodeinpsb.sl.AbsSpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialist
import java.io.Serializable
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.ArrayList


class CacheSpecialist : AbsSpecialist(), ICacheSpecialist {

    companion object {
        const val NAME = "CacheSpecialist"
        const val MAX_SIZE = 1000L
        const val DURATION: Long = 5
    }

    private val lock = ReentrantLock()
    private var cache: LoadingCache<String, Serializable>? = null
    private var value: Serializable? = null
    private val gson: Gson = Gson()

    override fun onRegister() {
        if (cache == null) {
            cache = CacheBuilder.newBuilder()
                .maximumSize(MAX_SIZE)
                .expireAfterWrite(DURATION, TimeUnit.MINUTES)
                .build(
                    object : CacheLoader<String, Serializable>() {
                        override fun load(key: String): Serializable? {
                            return value
                        }
                    })
        }
    }

    override fun put(key: String, value: Serializable?) {
        if (key.isEmpty()) {
            return
        }

        if (value == null) {
            return
        }

        if (!isValid()) {
            return
        }

        lock.lock()
        try {
            cache?.put(key, value)
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun putList(key: String, values: List<Serializable>?) {
        if (key.isEmpty()) {
            return
        }

        if (values == null) {
            return
        }

        if (!isValid()) {
            return
        }

        lock.lock()
        try {
            val s = toSerializable(values)
            cache?.put(key, s)
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun get(key: String): Serializable? {
        if (key.isEmpty()) {
            return null
        }

        lock.lock()
        try {
            return cache?.getIfPresent(key)
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return null
    }

    override fun getList(key: String): ArrayList<Serializable>? {
        if (key.isEmpty()) {
            return null
        }

        lock.lock()
        try {
            val s = cache?.getIfPresent(key)
            if (s != null) {
                return serializableToList(s)
            }
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
        return null
    }

    override fun isValid(): Boolean {
        val runtime = Runtime.getRuntime()
        val percent =
            100 - (runtime.totalMemory() - runtime.freeMemory()) * 100 / runtime.maxMemory()
        return percent >= 15
    }

    override fun clear(key: String) {
        if (key.isEmpty()) {
            return
        }

        lock.lock()
        try {
            cache?.invalidate(key)
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override fun stop() {
        super.stop()

        lock.lock()

        try {
            cache?.invalidateAll()
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            lock.unlock()
        }
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is ICacheSpecialist) 0 else 1
    }

    override fun getName(): String {
        return NAME
    }

    private fun toSerializable(list: List<Serializable>): Serializable {
        return LinkedList<Serializable>(list)
    }

    private fun serializableToList(value: Serializable?): ArrayList<Serializable>? {
        if (value == null) {
            return null
        }

        if (value is LinkedList<*>) {
            val items = value as LinkedList<Serializable>
            return ArrayList(items)
        } else if (value is ArrayList<*>) {
            return value as ArrayList<Serializable>
        }
        return null
    }

    override fun toJson(obj: Any): Serializable {
        return gson.toJson(obj)
    }

    override fun toJson(obj: Any, type: Type): Serializable {
        //use example : type = new com.google.gson.reflect.TypeToken<List<ContactItem>>(){}.getType()
        return gson.toJson(obj, type)
    }

    override fun <T> fromJson(json: String, cl: Class<T>): T {
        return gson.fromJson(json, cl)
    }

    override fun <T> fromJson(json: String, type: Type): T {
        // use example : type = new com.google.gson.reflect.TypeToken<List<ContactItem>>(){}.getType()
        return gson.fromJson<T>(json, type)
    }

}
