package shishkin.sl.kodeinpsb.sl

abstract class AbsProvider : IProvider {
    override fun isPersistent(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun onUnRegister() {
    }

    override fun onRegister() {
    }

    override fun stop() {
    }
}
