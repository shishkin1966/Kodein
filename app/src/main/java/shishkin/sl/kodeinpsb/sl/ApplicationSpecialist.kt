package shishkin.sl.kodeinpsb.sl

import android.app.Application

class ApplicationSpecialist : Application() {
    companion object{
        val instance = ApplicationSpecialist()
    }
}