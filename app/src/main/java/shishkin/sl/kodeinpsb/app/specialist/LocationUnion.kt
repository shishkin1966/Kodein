package shishkin.sl.kodeinpsb.app.specialist

import android.Manifest
import android.location.Address
import android.location.Location
import shishkin.sl.kodeinpsb.sl.AbsSmallUnion
import shishkin.sl.kodeinpsb.sl.ISpecialist
import com.google.android.gms.location.LocationServices
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import android.location.Geocoder
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.ShowMessageAction
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import java.util.concurrent.TimeUnit
import android.os.Looper
import java.util.*
import shishkin.sl.kodeinpsb.common.Connectivity










class LocationUnion : AbsSmallUnion<ILocationSubscriber>(), ILocationUnion,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    companion object {
        const val NAME = "LocationUnion"

        private val POLLING_FREQ = TimeUnit.SECONDS.toMillis(30)
        private val FASTEST_UPDATE_FREQ = TimeUnit.SECONDS.toMillis(5)
        private val SMALLEST_DISPLACEMENT = 20f
    }

    private var googleApiClient: GoogleApiClient? = null
    private var locationProviderClient: FusedLocationProviderClient? = null
    private var location: Location? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null
    private var geocoder: Geocoder? = null
    private var isGetLocation = false
    private var isRuning = false

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: ISpecialist): Int {
        return if (other is ILocationUnion) 0 else 1
    }

    override fun onRegister() {
        locationCallback = object : LocationCallback() {
            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                if (!locationAvailability!!.isLocationAvailable) {
                    val context = ApplicationSpecialist.appContext
                    ApplicationSingleton.instance.getActivityUnion()?.
                        addAction(ShowMessageAction(context.getString(R.string.location_error),ApplicationUtils.MESSAGE_TYPE_WARNING))
                }
            }

            override fun onLocationResult(locationResult: LocationResult?) {
                setLocation(locationResult?.lastLocation)
            }
        }

        googleApiClient = GoogleApiClient.Builder(ApplicationSpecialist.appContext)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        googleApiClient?.connect()
    }

    override fun onRegisterFirstSubscriber() {
        if (ApplicationUtils.checkPermission(
                ApplicationSpecialist.appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            startLocation()
        }
    }

    override fun onUnRegisterLastSubscriber() {
        stopLocation()
    }

    override fun startLocation() {
        if (!isValid()) return

        if (!hasSubscribers()) {
            return
        }

        if (isRuning) {
            stopLocation()
        }

        isRuning = true

        ApplicationUtils.runOnUiThread(Runnable{
            val context = ApplicationSpecialist.appContext
            if (ApplicationUtils.checkPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                if (locationRequest != null && locationCallback != null) {
                    isGetLocation = false
                    locationProviderClient =
                        LocationServices.getFusedLocationProviderClient(context)
                    locationProviderClient?.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.myLooper()
                    )
                        ?.addOnFailureListener { e -> ApplicationSingleton.instance.onError(NAME, e) }

                    if (geocoder == null) {
                        geocoder = Geocoder(context, Locale.getDefault())
                    }
                }
            }
        })

        if (location != null) {
            setLocation(location)
        }
    }

    override fun stopLocation() {
        isRuning = false
            locationProviderClient?.removeLocationUpdates(locationCallback)
            locationProviderClient = null
    }

    override fun getLocation(): Location? {
        if (location == null) {
            startLocation()
        }
        return location
    }

    override fun onAddSubscriber(subscriber: ILocationSubscriber) {
        if (subscriber.isValid() && location != null) {
            subscriber.setLocation(location!!)
        }
    }

    override fun getAddress(location: Location, countAddress: Int): List<Address> {
        var cnt = countAddress
        if (cnt < 1) {
            cnt = 1
        }

        val list = ArrayList<Address>()
        if (Connectivity.isNetworkConnectedOrConnecting(ApplicationSpecialist.appContext) && geocoder != null && Geocoder.isPresent()) {
            try {
                    val adr = geocoder?.getFromLocation(
                        location.latitude,
                        location.longitude,
                        cnt
                    )
                if (adr != null) {
                    list.addAll(adr)
                }
            } catch (e: Exception) {
                ApplicationSingleton.instance.onError(NAME, ApplicationSpecialist.appContext.getString(R.string.restart_location), true)
            }
        }
        return list
    }

    override fun getAddress(location: Location): List<Address> {
        return getAddress(location, 1)
    }

    override fun isGetLocation(): Boolean {
        return isGetLocation
    }

    override fun isRuning(): Boolean {
        return isRuning
    }

    private fun setLocation(location: Location?) {
        isGetLocation = true
        this.location = location

        if (location != null) {
            ApplicationUtils.runOnUiThread(Runnable{
                for (subscriber in getReadySubscribers()) {
                    subscriber.setLocation(this.location!!)
                }
            })
        }
    }

    override fun onConnected(p0: Bundle?) {
        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest?.interval = POLLING_FREQ
        locationRequest?.fastestInterval = FASTEST_UPDATE_FREQ
        locationRequest?.smallestDisplacement = SMALLEST_DISPLACEMENT
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        ApplicationSingleton.instance.onError(NAME, "Connection failed", true)
    }

    override fun onConnectionSuspended(p0: Int) {
        ApplicationSingleton.instance.onError(NAME, "Connection suspended", true)
    }

    override fun stop() {
        stopLocation()
        super.stop()
    }

    override fun isValid(): Boolean {
        val context = ApplicationSpecialist.appContext
            if (!ApplicationUtils.isGooglePlayServices(context)) {
                return false
            }

            if (!ApplicationUtils.checkPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                return false
            }

            if (!ApplicationUtils.isLocationEnabled(context)) {
                return false
            }
        return true
    }

}
