package shishkin.sl.kodeinpsb.app.screen.digital_currencies

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.action.OnEditTextChangedAction
import shishkin.sl.kodeinpsb.app.data.Ticker
import shishkin.sl.kodeinpsb.app.provider.Provider
import shishkin.sl.kodeinpsb.app.request.GetTickersRequest
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.*
import shishkin.sl.kodeinpsb.sl.data.ExtResult
import shishkin.sl.kodeinpsb.sl.presenter.AbsModelPresenter
import shishkin.sl.kodeinpsb.sl.request.IResponseListener
import shishkin.sl.kodeinpsb.sl.request.Request


class DigitalCurrenciesPresenter(model: DigitalCurrenciesModel) : AbsModelPresenter(model),
    IResponseListener {
    companion object {
        const val NAME = "DigitalCurrenciesPresenter"
        const val InitFilter = "InitFilter"
    }

    private lateinit var data: TickerData

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        if (!::data.isInitialized) {
            data = TickerData()
            getView<DigitalCurrenciesFragment>().addAction(DataAction(InitFilter, data))
            val temp =
                ApplicationSingleton.instance.getCache().getList(GetTickersRequest.NAME) as ArrayList<Ticker>?
            if (temp != null) {
                data.tickers = temp
                getView<DigitalCurrenciesFragment>().addAction(DataAction(Actions.RefreshViews, data))
            }
            getData()
        } else {
            getView<DigitalCurrenciesFragment>().addAction(DataAction(Actions.RefreshViews, data))
        }
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            when (action.getName()) {
                Actions.OnSwipeRefresh -> {
                    getData()
                    return true
                }
            }
        }

        if (action is OnEditTextChangedAction) {
            if (!::data.isInitialized) {
                data = TickerData()
            }
            val arg = action.obj as String?
            if (arg != data.filter) {
                data.filter = arg
                getView<DigitalCurrenciesFragment>().addAction(
                    DataAction(
                        Actions.RefreshViews,
                        data
                    )
                )
            }
            return true
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    private fun getData() {
        getView<DigitalCurrenciesFragment>().addAction(ShowProgressBarAction())
        Provider.getTickers(getName())
    }

    override fun response(result: ExtResult) {
        getView<DigitalCurrenciesFragment>().addAction(HideProgressBarAction())
        if (!result.hasError()) {
            data.tickers = ArrayList(result.getData() as List<Ticker>)
            getView<DigitalCurrenciesFragment>().addAction(DataAction(Actions.RefreshViews, data))
            ApplicationSingleton.instance.getExecutor().execute(object : Request() {
                override fun run() {
                    ApplicationSingleton.instance.getCache()
                        .put(GetTickersRequest.NAME, data.tickers)
                }
            })
        } else {
            getView<DigitalCurrenciesFragment>().addAction(
                ShowMessageAction(result.getErrorText()).setType(
                    ApplicationUtils.MESSAGE_TYPE_ERROR
                )
            )
        }
    }

    override fun onDestroyView() {
        data.saveFilter()

        super.onDestroyView()
    }
}
