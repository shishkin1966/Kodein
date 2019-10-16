package shishkin.sl.kotlin.app.screen.setting

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.setting.Setting
import shishkin.sl.kotlin.common.ApplicationUtils
import shishkin.sl.kotlin.common.LinearLayoutBehavior
import shishkin.sl.kotlin.sl.action.Actions
import shishkin.sl.kotlin.sl.action.ApplicationAction
import shishkin.sl.kotlin.sl.action.DataAction
import shishkin.sl.kotlin.sl.action.IAction
import shishkin.sl.kotlin.sl.action.handler.FragmentActionHandler
import shishkin.sl.kotlin.sl.model.IModel
import shishkin.sl.kotlin.sl.provider.ApplicationProvider
import shishkin.sl.kotlin.sl.ui.AbsContentFragment


class SettingFragment : AbsContentFragment(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    companion object {
        const val NAME = "SettingFragment"

        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var listView: LinearLayout
    private val actionHandler = FragmentActionHandler(this)

    override fun createModel(): IModel {
        return SettingModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior = LinearLayoutBehavior.from(findView(R.id.bottomSheetContainer))
        listView = view.findViewById(R.id.list)

        if (!ApplicationUtils.checkPermission(
                ApplicationProvider.appContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            view.findViewById<View>(R.id.db_backup).isEnabled = false
            view.findViewById<View>(R.id.db_restore).isEnabled = false
        }

        view.findViewById<View>(R.id.db_backup).setOnClickListener(this)
        view.findViewById<View>(R.id.db_restore).setOnClickListener(this)
    }

    override fun getName(): String {
        return NAME
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.db_backup -> {
                getModel<SettingModel>().getPresenter<SettingPresenter>()
                    .addAction(ApplicationAction(SettingPresenter.BackupDb))
            }
            R.id.db_restore -> {
                getModel<SettingModel>().getPresenter<SettingPresenter>()
                    .addAction(ApplicationAction(SettingPresenter.RestoreDb))
            }
            R.id.ll -> {
                val setting = v.tag as Setting?
                if (setting != null) {
                    when (setting.type) {
                        Setting.TYPE_LIST -> {
                            getModel<SettingModel>().getPresenter<SettingPresenter>()
                                .addAction(DataAction(SettingPresenter.ShowListSetting, setting))
                        }
                    }
                }
            }
        }
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.RefreshViews -> {
                    refreshViews(action.getData() as SettingsData)
                    return true
                }
                SettingPresenter.DBCopyEnabledAction -> {
                    view?.findViewById<View>(R.id.db_restore)?.isEnabled =
                        action.getData() as Boolean
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

    private fun refreshViews(data: SettingsData) {
        listView.removeAllViews()

        for (setting in data.settings) {
            val view = getView(listView, setting)
            if (view != null) {
                listView.addView(view)
            }
        }
    }

    private fun getView(parent: ViewGroup, setting: Setting): View? {
        var view: View? = null
        var titleView: TextView?
        val currentValue: String?

        when (setting.type) {
            Setting.TYPE_TEXT -> {
                view = layoutInflater.inflate(R.layout.setting_item_text, parent, false)
                titleView = view.findViewById<TextView>(R.id.item_title)
                titleView.text = setting.title
            }

            Setting.TYPE_SWITCH -> {
                view = layoutInflater.inflate(R.layout.setting_item_switch, parent, false)
                titleView = view.findViewById(R.id.item_title)
                titleView.text = setting.title

                val valueView = view.findViewById<SwitchCompat>(R.id.item_switch)
                currentValue = setting.current
                valueView.isChecked = currentValue!!.toBoolean()
                valueView.tag = setting
                valueView.setOnCheckedChangeListener(this)
            }

            Setting.TYPE_LIST -> {
                view = layoutInflater.inflate(R.layout.setting_item_list, parent, false)
                view.findViewById<View>(R.id.ll).tag = setting
                view.findViewById<View>(R.id.ll).setOnClickListener(this)
                titleView = view.findViewById(R.id.item_title)
                titleView.text = setting.title
                titleView = view.findViewById(R.id.item_value)
                titleView.text = setting.current
            }
        }
        return view
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val setting = buttonView?.tag as Setting?
        if (setting != null) {
            setting.current = isChecked.toString()
        }
        getModel<SettingModel>().getPresenter<SettingPresenter>()
            .addAction(DataAction(SettingPresenter.SettingChangedAction, setting))
    }
}

