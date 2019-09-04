package shishkin.sl.kodeinpsb.sl

interface ISpecialistFactory {
    fun create(name: String): ISpecialist?
}