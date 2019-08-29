package shishkin.sl.kodeinpsb.sl


interface ISecretary<T> {
    fun remove(key: String): T?

    fun size(): Int

    fun put(key: String, value: T?): T?

    fun containsKey(key: String): Boolean

    operator fun get(key: String): T?

    fun values(): List<T>

    fun isEmpty(): Boolean

    fun clear()

    fun keys(): Collection<String>

}