package shishkin.sl.kodeinpsb.sl.specialist

import android.os.Parcelable

import shishkin.sl.kodeinpsb.sl.ISpecialist
import java.io.Serializable
import java.lang.reflect.Type


interface ICacheSpecialist : ISpecialist {
    /**
     * Put value to cache.
     *
     * @param key   the key
     * @param value the value
     */
    fun put(key: String, value: Serializable?)

    /**
     * Put list to cache.
     *
     * @param key    the key
     * @param values the list
     */
    fun putList(key: String, values: List<Serializable>?)

    /**
     * Get value from cache.
     *
     * @param key       the key
     * @return the Serializable
     */
    operator fun get(key: String): Serializable?

    /**
     * Get list from cache.
     *
     * @param key       the key
     * @return the list
     */
    fun getList(key: String): ArrayList<Serializable>?

    /**
     * delete value from cache
     *
     * @param key the key
     */
    fun clear(key: String)

    fun toJson(obj: Any): Serializable

    fun toJson(obj: Any, type: Type): Serializable

    fun <T> fromJson(json: String, cl: Class<T>): T

    fun <T> fromJson(json: String, type: Type): T
}
