package shishkin.sl.kodeinpsb.app.screen.map

import android.Manifest
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.SupportMapFragment
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View


class MapFragment : AbsContentFragment() {
    companion object {
        const val NAME = "MapFragment"

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun createModel(): IModel {
        return MapModel(this)
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        startMap()
    }

    private fun startMap() {
        if (ApplicationUtils.checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            val mapOptions = GoogleMapOptions()
                .compassEnabled(true)
                .zoomControlsEnabled(false)
                .mapType(GoogleMap.MAP_TYPE_NORMAL)
            val fragment = SupportMapFragment.newInstance(mapOptions)
            fragment.getMapAsync(getModel<MapModel>()?.getPresenter())
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(R.id.map, fragment, "map")
            transaction.commit()
        }
    }

    override fun onBackPressed(): Boolean {
        ApplicationSingleton.instance.getActivityUnion()?.switchToTopFragment()
        return true
    }

}
