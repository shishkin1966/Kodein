package shishkin.sl.kodeinpsb.sl

abstract class AbsSpecialist : ISpecialist {
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