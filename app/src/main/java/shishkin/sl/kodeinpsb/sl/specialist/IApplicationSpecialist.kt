package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISpecialist

interface IApplicationSpecialist : ISpecialist {
    fun isExit() : Boolean

    fun exit()
}