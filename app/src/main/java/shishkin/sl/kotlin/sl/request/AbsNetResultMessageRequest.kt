package shishkin.sl.kotlin.sl.request

import io.reactivex.Single
import shishkin.sl.kotlin.sl.data.ExtError
import shishkin.sl.kotlin.sl.data.ExtResult
import shishkin.sl.kotlin.sl.message.ResultMessage
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.provider.IMessengerUnion
import shishkin.sl.kotlin.sl.provider.MessengerUnion


abstract class AbsNetResultMessageRequest(owner: String) : AbsResultMessageRequest(owner) {

    override fun run() {
        if (!isValid()) return
        val union = ApplicationProvider.serviceLocator?.get<IMessengerUnion>(
            MessengerUnion.NAME
        )
        if (union == null) return

        val single = getData() as Single<*>
        single
            .map { t: Any -> ExtResult(t) }
            .subscribe(
                { result: ExtResult ->
                    if (isValid() && result.getData() != null) {
                        union.addNotMandatoryMessage(
                            ResultMessage(
                                getOwner(),
                                result.setName(getName()).setVersion(getVersion())
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
                        ).setName(getName()).setVersion(getVersion())
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
