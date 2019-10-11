package shishkin.sl.kodeinpsb.app.screen.setting

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
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.setting.Setting
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.common.LinearLayoutBehavior
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.action.handler.FragmentActionHandler
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment


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
                ApplicationSpecialist.appContext,
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
                    ApplicationUtils.runOnUiThread(Runnable {
                        view?.findViewById<View>(R.id.db_restore)?.isEnabled =
                            action.getData() as Boolean
                    })
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
        ApplicationUtils.runOnUiThread(Runnable {
            listView.removeAllViews()

            for (setting in data.settings) {
                val view = getView(listView, setting)
                if (view != null) {
                    listView.addView(view)
                }
            }
        })
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

