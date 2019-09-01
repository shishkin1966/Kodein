package shishkin.sl.kodeinpsb.sl

interface ISpecialistFactory {
    fun <T : ISpecialist> create(name: String): T
}