package shishkin.sl.kodeinpsb.sl.request

import io.reactivex.Single
import shishkin.sl.kodeinpsb.sl.data.ExtError
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.message.ResultMessage
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerUnion
import shishkin.sl.kodeinpsb.sl.specialist.MessengerUnion


abstract class AbsNetResultMessageRequest(owner: String) : AbsResultMessageRequest(owner) {

    override fun run() {
        if (!isValid()) return
        val union = ApplicationSpecialist.serviceLocator?.get<IMessengerUnion>(MessengerUnion.NAME)
        if (union == null) return

        val single = getData() as Single<*>
        single
            ?.map { t: Any -> ExtResult(t) }
            ?.subscribe(
                { result: ExtResult ->
                    if (isValid() && result.getData() != null) {
                        union.addNotMandatoryMessage(
                            ResultMessage(
                                getOwner(),
                                result.setOrder(ExtResult.LAST).setName(getName())
                            )
                                .setSubj(getName())
                                .setCopyTo(getCopyTo())
                        )
                    }
                }, { throwable: Throwable ->
                    if (isValid()) {
                        val result = ExtResult().setError(
                            ExtError().addError(
                                getName(),
                                throwable.getLocalizedMessage()
                            )
                        ).setName(getName()).setOrder(ExtResult.LAST)
                        union.addNotMandatoryMessage(
                            ResultMessage(getOwner(), result)
                                .setSubj(getName())
                                .setCopyTo(getCopyTo())
                        )
                    }
                }
            )

    }

}
