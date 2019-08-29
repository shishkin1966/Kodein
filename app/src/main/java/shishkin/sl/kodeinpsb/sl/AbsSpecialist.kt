package shishkin.sl.kodeinpsb.sl

abstract class AbsSpecialist<T> : ISpecialist<T> {
    override fun onUnRegister() {}

    override fun onRegister() {}

    override fun isPersistent(): Boolean {
        return false
    }

    override fun validate(): Boolean {
        return true
    }

    override fun stop() {}

}