package shishkin.sl.kodeinpsb.sl.task

import android.os.Handler
import java.util.concurrent.Executor
import android.os.Looper



class MainThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        handler.post(command)
    }
}
