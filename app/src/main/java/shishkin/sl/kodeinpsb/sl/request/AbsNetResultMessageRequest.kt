package shishkin.sl.kodeinpsb.sl.request

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import shishkin.sl.kodeinpsb.sl.data.ExtError
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.message.ResultMessage
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerUnion
import shishkin.sl.kodeinpsb.sl.specialist.MessengerUnion


abstract class AbsNetResultMessageRequest<T : Single<T>> : AbsResultMessageRequest<T> {

    private constructor() : super()

    constructor(owner: String) : super(owner)

    override fun run() {
        if (!validate()) return
        val union = ApplicationSpecialist.serviceLocator?.get<IMessengerUnion>(MessengerUnion.NAME)
        if (union == null) return

        getData()
            .map({ t: T -> ExtResult(t) })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { result: ExtResult ->
                    if (validate() && result.getData() != null) {
                        union.addNotMandatoryMessage(
                            ResultMessage(
                                getOwner(),
                                result.setOrder(ExtResult.LAST).setName(getName())
                            ).setName(getName()).setCopyTo(getCopyTo())
                        )
                    }
                }, Consumer { throwable: Throwable ->
                    if (validate()) {
                        val result = ExtResult().setError(
                            ExtError().addError(
                                getName(),
                                throwable.getLocalizedMessage()
                            )
                        ).setName(getName()).setOrder(ExtResult.LAST)
                        union.addNotMandatoryMessage(
                            ResultMessage(getOwner(), result).setName(
                                getName()
                            ).setCopyTo(getCopyTo())
                        )
                    }
                }
            )
    }


}