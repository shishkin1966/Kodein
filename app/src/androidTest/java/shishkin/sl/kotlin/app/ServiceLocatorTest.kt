package shishkin.sl.kotlin.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ServiceLocatorTest {

    @Test
    fun start() {
        val sl = ServiceLocatorSingleton.instance
        assert(sl != null)
    }
}
