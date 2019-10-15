package shishkin.sl.kodeinpsb.app.screen.digital_currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.action.OnEditTextChangedAction
import shishkin.sl.kodeinpsb.app.observe.EditTextObservable
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.FragmentActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.provider.ApplicationProvider
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment
import java.util.*


class DigitalCurrenciesFragment : AbsContentFragment(), SwipeRefreshLayout.OnRefreshListener,
    Observer {

    companion object {
        const val NAME = "DigitalCurrenciesFragment"

        fun newInstance(): DigitalCurrenciesFragment {
            return DigitalCurrenciesFragment()
        }
    }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val actionHandler = FragmentActionHandler(this)
    private lateinit var recyclerView: RecyclerView
    private val adapter: TickerRecyclerViewAdapter = TickerRecyclerViewAdapter()
    private var observable: EditTextObservable? = null
    private lateinit var searchView: EditText

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

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeResources(R.color.blue)
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.gray_light)
        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        searchView = view.findViewById(R.id.search)
        val context = ApplicationProvider.appContext
        searchView.setCompoundDrawablesWithIntrinsicBounds(
            ApplicationUtils.getVectorDrawable(
                context,
                R.drawable.magnify,
                context.theme
            ), null, null, null
        )

        view.findViewById<View>(R.id.clear).setOnClickListener {
            searchView.setText("")
        }
    }

    override fun onRefresh() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        getModel<DigitalCurrenciesModel>().getPresenter<DigitalCurrenciesPresenter>()
            .addAction(ApplicationAction(Actions.OnSwipeRefresh))
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.RefreshViews -> {
                    refreshViews(action.getData() as TickerData?)
                    return true
                }
                DigitalCurrenciesPresenter.InitFilter -> {
                    initFilter(action.getData() as TickerData?)
                    return true
                }
            }
        }

        if (actionHandler.onAction(action)) return true

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        recyclerView.adapter = null
    }

    private fun refreshViews(viewData: TickerData?) {
        if (viewData == null) return

        adapter.setItems(viewData.getData())
    }

    override fun update(o: Observable?, arg: Any?) {
        getModel<DigitalCurrenciesModel>().getPresenter<DigitalCurrenciesPresenter>().addAction(
            OnEditTextChangedAction(o, arg)
        )
    }

    private fun initFilter(viewData: TickerData?) {
        observable?.finish()
        searchView.setText(viewData?.filter)
        observable = EditTextObservable(this, searchView)
    }
}
