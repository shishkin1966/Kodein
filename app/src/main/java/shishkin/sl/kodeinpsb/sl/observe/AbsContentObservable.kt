package shishkin.sl.kodeinpsb.sl.observe

import android.net.Uri
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist


abstract class AbsContentObservable() : AbsObservable() {

    private val observer = BaseContentObserver(this)
    private val uris = ArrayList<Uri>()

    constructor(uri: Uri) : this() {
        uris.add(uri);
    }

    constructor(uris: List<Uri>) : this() {
        this.uris.addAll(uris)
    }

    override fun register() {
        val context = ApplicationSpecialist.instance
        for (uri in uris) {
            context.getContentResolver().registerContentObserver(uri, true, observer)
        }
    }

    override fun unregister() {
        val context = ApplicationSpecialist.instance;
        context.contentResolver.unregisterContentObserver(observer);
    }
}
