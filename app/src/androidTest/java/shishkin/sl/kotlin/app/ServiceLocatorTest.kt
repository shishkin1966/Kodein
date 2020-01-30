package shishkin.sl.kotlin.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ServiceLocatorTest {
    private lateinit var sl : ServiceLocator

    @Before
    fun init() {
        sl = ServiceLocatorSingleton.instance
    }

    @Test
    fun start() {
        assertNotNull("Service Locator not found", sl)
    }

    @Test
    fun testActivityProvider() {
        assertNotNull("Activity Provider not found", ApplicationSingleton.instance.activityProvider)
        assertTrue("Activity Provider not valid", ApplicationSingleton.instance.activityProvider.isValid())
    }

    @Test
    fun testDbProvider() {
        assertNotNull("Db Provider not found", ApplicationSingleton.instance.dbProvider)
        assertTrue("Db Provider not valid", ApplicationSingleton.instance.dbProvider.isValid())
    }

    @Test
    fun testObservableProvider() {
        assertNotNull("Observable Provider not found", ApplicationSingleton.instance.observableProvider)
        assertTrue("Observable Provider not valid", ApplicationSingleton.instance.observableProvider.isValid())
    }

    @Test
    fun testLocationProvider() {
        assertNotNull("Location Provider not found", ApplicationSingleton.instance.locationProvider)
        assertTrue("Location Provider not valid", ApplicationSingleton.instance.locationProvider.isValid())
    }

    @Test
    fun testCacheProvider() {
        assertNotNull("Cache Provider not found", ApplicationSingleton.instance.cacheProvider)
        assertTrue("Cache Provider not valid", ApplicationSingleton.instance.cacheProvider.isValid())
    }

    @Test
    fun testNotificationProvider() {
        assertNotNull("Notification Provider not found", ApplicationSingleton.instance.notificationProvider)
        assertTrue("Notification Provider not valid", ApplicationSingleton.instance.notificationProvider.isValid())
    }

    @Test
    fun testNetApi() {
        assertNotNull("Net Api not found", ApplicationSingleton.instance.getApi())
    }

    @Test
    fun testDao() {
        assertNotNull("Dao not found", ApplicationSingleton.instance.getDao())
    }
}
