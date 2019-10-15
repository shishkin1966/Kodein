package shishkin.sl.kodeinpsb.sl

interface IProviderFactory {
    fun create(name: String): IProvider?
}
