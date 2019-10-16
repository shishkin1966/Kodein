package shishkin.sl.kotlin.sl

interface IProviderFactory {
    fun create(name: String): IProvider?
}
