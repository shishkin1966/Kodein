package shishkin.sl.kodeinpsb.sl.specialist

import android.os.Parcelable

import shishkin.sl.kodeinpsb.sl.ISpecialist


interface ICacheSpecialist : ISpecialist {
    /**
     * Put value to cache.
     *
     * @param key   the key
     * @param value the value
     */
    fun <T : Parcelable> put(key: String, value: T)

    /**
     * Put list to cache.
     *
     * @param key    the key
     * @param values the value
     */
    fun <T : Parcelable> put(key: String, values: List<T>)

    /**
     * Get value from cache.
     *
     * @param key       the key
     * @param itemClass the value class
     * @return the Parcelable
     */
    operator fun <T : Parcelable> get(key: String, itemClass: Class<*>): T?

    /**
     * Get list from cache.
     *
     * @param key       the key
     * @param itemClass the value class
     * @return the list
     */
    fun <T : Parcelable> getList(key: String, itemClass: Class<*>): ArrayList<T>?

    /**
     * delete value from cache
     *
     * @param key the key
     */
    fun clear(key: String)

}
