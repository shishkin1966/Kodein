package shishkin.sl.kodeinpsb.app.screen.digital_currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.FragmentActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment


class DigitalCurrenciesFragment : AbsContentFragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        const val NAME = "DigitalCurrenciesFragment"

        fun newInstance(): DigitalCurrenciesFragment {
            return DigitalCurrenciesFragment()
        }
    }

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private val actionHandler = FragmentActionHandler(this)

    override fun createModel(): IModel {
        return DigitalCurrenciesModel(this)
    }

    override fun getName(): String {
        return NAME
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_digital_currencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        swipeRefreshLayout?.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout?.setProgressBackgroundColorSchemeResource(R.color.gray_light);
        swipeRefreshLayout?.setOnRefreshListener(this);

    }

    override fun onRefresh() {
        if (swipeRefreshLayout?.isRefreshing == true) {
            swipeRefreshLayout?.isRefreshing = false
        }
        getModel<DigitalCurrenciesModel>()?.getPresenter<DigitalCurrenciesPresenter>()
            ?.addAction(ApplicationAction(Actions.OnSwipeRefresh));
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (actionHandler.onAction(action)) return true

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }
}
