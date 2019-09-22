package shishkin.sl.kodeinpsb.common

import android.os.Handler

/**
 * Класс, устраняющий дребезг (частое повторение) события
 *
 * @param delay задержка, после которой запустится действие
 * @param skip  количество событий, которое будет пропущено перед запуском задержки
 */
open class Debounce (delay: Long, skip: Int = 0) : Runnable {

    private var _delay: Long = 5000 //5 sec
    private var _skip = 0
    private val handler: Handler = Handler()

    init {
        _delay = delay
        _skip = skip
    }

    /**
     * Событие
     */
    fun onEvent() {
        if (_skip >= 0) {
            _skip--
        }

        if (_skip < 0) {
            handler.removeCallbacks(this)
            handler.postDelayed(this, _delay)
        }
    }

    override fun run() {}

    /**
     * остановить объект
     */
    fun finish() {
        handler.removeCallbacks(this)
    }

}

