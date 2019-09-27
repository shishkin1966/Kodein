package shishkin.sl.kodeinpsb.sl.specialist


import android.os.Parcel
import android.os.Parcelable
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import shishkin.sl.kodeinpsb.sl.AbsSpecialist
import shishkin.sl.kodeinpsb.sl.ISpecialist
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class CacheSpecialist : AbsSpecialist(), ICacheSpecialist {

    companion object {

        val NAME = "CacheSpecialist"

        const val PARCELABLE = "PARCELABLE"
        const val LIST = "LIST"
        const val MAX_SIZE = 1000L
        const val DURATION: Long = 1
    }

    private val mLock = ReentrantLock()
    private var mCache: LoadingCache<String, ByteArray>? = null
    private var mValue: ByteArray? = null

    override fun onRegister() {
        if (mCache == null) {
            mCache = CacheBuilder.newBuilder()
                .maximumSize(MAX_SIZE)
                .expireAfterWrite(DURATION, TimeUnit.MINUTES)
                .build(
                    object : CacheLoader<String, ByteArray>() {
                        override fun load(key: String): ByteArray? {
                            return mValue
                        }
                    })
        }
    }

    override fun <T : Parcelable> put(key: String, value: T) {
        if (key.isNullOrEmpty() || mCache == null) {
            return
        }

        if (!isValid()) {
            return
        }

        var parcel: Parcel? = null

        mLock.lock()

        try {
            parcel = Parcel.obtain()
            parcel!!.writeString(PARCELABLE)
            parcel.writeParcelable(value, 0)
            mValue = parcel.marshall()
            mCache!!.put(key, mValue!!)
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            parcel?.recycle()
            mLock.unlock()
        }
    }

    override fun <T : Parcelable> put(key: String, values: List<T>) {
        if (key.isNullOrEmpty()) {
            return
        }

        if (!isValid()) {
            return
        }

        var parcel: Parcel? = null

        mLock.lock()

        try {
            parcel = Parcel.obtain()
            parcel!!.writeString(LIST)
            parcel.writeList(values)
            mValue = parcel.marshall()
            mCache!!.put(key, mValue!!)
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            parcel?.recycle()
            mLock.unlock()
        }
    }

    override fun <T : Parcelable> get(key: String, itemClass: Class<*>): T? {
        if (key.isNullOrEmpty()) {
            return null
        }

        var parcel: Parcel? = null

        mLock.lock()

        try {
            parcel = Parcel.obtain()
            val value = mCache!!.getIfPresent(key)
            if (value != null) {
                parcel!!.unmarshall(value, 0, value.size)
                parcel.setDataPosition(0)
                val type = parcel.readString()
                if (PARCELABLE == type) {
                    return parcel.readParcelable<Parcelable>(itemClass.classLoader) as T
                }
            }
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            parcel?.recycle()
            mLock.unlock()
        }
        return null
    }

    override fun <T : Parcelable> getList(key: String, itemClass: Class<*>): ArrayList<T>? {
        if (key.isNullOrEmpty()) {
            return null
        }

        var parcel: Parcel? = null

        mLock.lock()

        try {
            parcel = Parcel.obtain()
            val value = mCache!!.getIfPresent(key)
            if (value != null) {
                parcel!!.unmarshall(value, 0, value.size)
                parcel.setDataPosition(0)
                val type = parcel.readString()
                if (LIST == type) {
                    val res = ArrayList<T>()
                    parcel.readList(res, itemClass.classLoader)
                    return res
                }
            }
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            parcel?.recycle()
            mLock.unlock()
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
        if (key.isNullOrEmpty()) {
            return
        }

        mLock.lock()

        try {
            mCache!!.invalidate(key)
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            mLock.unlock()
        }
    }

    override fun stop() {
        super.stop()

        mLock.lock()

        try {
            mCache!!.invalidateAll()
        } catch (e: Exception) {
            ErrorSpecialistSingleton.instance.onError(NAME, e)
        } finally {
            mLock.unlock()
        }
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is ICacheSpecialist) 0 else 1
    }

    override fun getName(): String {
        return NAME
    }

}
