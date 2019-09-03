package microservices.shishkin.sl.ui

import android.content.Context
import android.os.Bundle

import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.app.ServiceLocatorSingleton
import shishkin.sl.kodeinpsb.common.ApplicationUtils
import shishkin.sl.kodeinpsb.sl.action.DialogResultAction
import shishkin.sl.kodeinpsb.sl.message.DialogResultMessage
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerUnion
import shishkin.sl.kodeinpsb.sl.specialist.MessengerUnion


import java.util.ArrayList


class MaterialDialogExt {

    private var id: Int = 0
    private val materialDialog: MaterialDialog
    private val listener: String

    constructor(
        context: Context, listener: String, id: Int,
        title: String, message: String, positiveButton: Int,
        setCancelable: Boolean
    ) : this(
        context,
        listener,
        id,
        title,
        message,
        positiveButton,
        NO_BUTTON,
        NO_BUTTON,
        setCancelable
    ) {
    }

    constructor(
        context: Context, listener: String, id: Int,
        title: String, message: String, positiveButton: Int,
        negativeButton: Int, setCancelable: Boolean
    ) : this(
        context,
        listener,
        id,
        title,
        message,
        positiveButton,
        negativeButton,
        NO_BUTTON,
        setCancelable
    ) {
    }

    constructor(
        context: Context, listener: String, id: Int,
        title: String, message: String, positiveButton: Int,
        negativeButton: Int, neutralButton: Int, setCancelable: Boolean
    ) {

        this.id = id
        this.listener = listener

        val builder = MaterialDialog.Builder(context)
        if (!title.isNullOrEmpty()) {
            builder.title(title)
        }
        if (!message.isNullOrEmpty()) {
            builder.content(message)
        }
        if (positiveButton != NO_BUTTON) {
            builder.positiveText(positiveButton)
            builder.onPositive({ dialog, which ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, POSITIVE)
                    if (!this.listener.isNullOrEmpty()) {
                        val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                        if (union != null) {
                            union.addMessage(
                                DialogResultMessage(
                                    this.listener,
                                    DialogResultAction(bundle, this.id)
                                )
                            )
                        }
                    }
                }
            })
        }
        if (negativeButton != NO_BUTTON) {
            builder.negativeText(negativeButton)
            builder.onNegative({ dialog, which ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, NEGATIVE)
                    if (!this.listener.isNullOrEmpty()) {
                        val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                        if (union != null) {
                            union.addMessage(
                                DialogResultMessage(
                                    this.listener,
                                    DialogResultAction(bundle, this.id)
                                )
                            )
                        }
                    }
                }
            })
        }
        if (neutralButton != NO_BUTTON) {
            builder.neutralText(neutralButton)
            builder.onNeutral({ dialog, which ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, NEUTRAL)
                    if (!this.listener.isNullOrEmpty()) {
                        val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                        if (union != null) {
                            union.addMessage(
                                DialogResultMessage(
                                    this.listener,
                                    DialogResultAction(bundle, this.id)
                                )
                            )
                        }
                    }
                }
            })
        }
        builder.cancelable(setCancelable)

        materialDialog = builder.build()
    }

    constructor(
        context: Context,
        listener: String,
        id: Int,
        title: String,
        message: String,
        items: List<String>,
        selected: Array<Int>?,
        multiselect: Boolean,
        positiveButton: Int,
        negativeButton: Int,
        setCancelable: Boolean
    ) {

        this.id = id
        this.listener = listener

        val builder = MaterialDialog.Builder(context)
        if (!title.isNullOrEmpty()) {
            builder.title(title)
        }
        if (!message.isNullOrEmpty()) {
            builder.content(message)
        }
        builder.items(items)
        if (multiselect) {
            builder.itemsCallbackMultiChoice(null, { dialog, which, text -> true })
        } else {
            builder.alwaysCallSingleChoiceCallback()
            builder.itemsCallbackSingleChoice(-1, { dialog, view, which, text ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt(ID, this.id)
                    bundle.putString(BUTTON, POSITIVE)
                    val list = ArrayList<String>()
                    list.add(text.toString())
                    bundle.putStringArrayList("list", list)
                    if (!this.listener.isNullOrEmpty()) {
                        val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                        if (union != null) {
                            union.addMessage(
                                DialogResultMessage(
                                    this.listener,
                                    DialogResultAction(bundle, this.id)
                                )
                            )
                        }
                    }
                }
                dialog.dismiss()
                true
            })
        }
        if (multiselect) {
            if (positiveButton != NO_BUTTON) {
                builder.positiveText(positiveButton)
            }
        }
        if (negativeButton != NO_BUTTON) {
            builder.negativeText(negativeButton)
        }
        if (multiselect) {
            builder.onPositive({ dialog, which ->
                if (this.id > -1) {
                    val bundle = Bundle()
                    bundle.putInt("id", this.id)
                    bundle.putString(BUTTON, POSITIVE)
                    val list = ArrayList<String>()
                    val itemsCharSequence = dialog.getItems()
                    val selected1 = dialog.getSelectedIndices()
                    for (i in selected1!!) {
                        list.add(itemsCharSequence!!.get(i!!).toString())
                    }
                    bundle.putStringArrayList("list", list)
                    if (!this.listener.isNullOrEmpty()) {
                        val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                        if (union != null) {
                            union.addMessage(
                                DialogResultMessage(
                                    this.listener,
                                    DialogResultAction(bundle, this.id)
                                )
                            )
                        }
                    }
                }
            })
        }
        builder.onNegative({ dialog, which ->
            if (this.id > -1) {
                val bundle = Bundle()
                bundle.putInt(ID, this.id)
                bundle.putString(BUTTON, NEGATIVE)
                if (!this.listener.isNullOrEmpty()) {
                    val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                    if (union != null) {
                        union.addMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
            }
        })
        builder.cancelable(setCancelable)

        materialDialog = builder.build()
        if (selected != null) {
            materialDialog?.setSelectedIndices(selected)
        }
    }


    constructor(
        context: Context,
        listener: String,
        id: Int,
        title: String,
        message: String,
        edittext: String,
        hint: String,
        input_type: Int,
        positiveButton: Int,
        negativeButton: Int,
        setCancelable: Boolean
    ) {

        this.id = id
        this.listener = listener

        val builder = MaterialDialog.Builder(context)
        if (!title.isNullOrEmpty()) {
            builder.title(title)
        }
        if (!message.isNullOrEmpty()) {
            builder.content(message)
        }
        builder.positiveText(positiveButton)
        if (negativeButton != NO_BUTTON) {
            builder.negativeText(negativeButton)
        }
        builder.inputType(input_type)
        builder.input(hint, edittext, { dialog, input -> })
        builder.onPositive({ dialog, which ->
            if (this.id > -1) {
                val bundle = Bundle()
                bundle.putInt(ID, this.id)
                bundle.putString(BUTTON, POSITIVE)
                bundle.putString("object", dialog.getInputEditText()?.getText().toString())
                if (!this.listener.isNullOrEmpty()) {
                    val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                    if (union != null) {
                        union.addMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
            }
        })
        builder.onNegative({ dialog, which ->
            if (this.id > -1) {
                val bundle = Bundle()
                bundle.putInt(ID, this.id)
                bundle.putString(BUTTON, NEGATIVE)
                if (!this.listener.isNullOrEmpty()) {
                    val union = ServiceLocatorSingleton.instance.get<IMessengerUnion>(MessengerUnion.NAME)
                    if (union != null) {
                        union.addMessage(
                            DialogResultMessage(
                                this.listener,
                                DialogResultAction(bundle, this.id)
                            )
                        )
                    }
                }
            }
        })
        builder.cancelable(setCancelable)

        materialDialog = builder.build()
    }

    fun show() {
        if (materialDialog != null) {
            var size = ApplicationUtils.px2sp(
                materialDialog!!.getContext(),
                materialDialog!!.getContext().getResources().getDimension(R.dimen.text_size_large)
            )
            materialDialog!!.getActionButton(DialogAction.POSITIVE).setTextSize(size)
            materialDialog!!.getActionButton(DialogAction.POSITIVE)
                .setTextColor(ApplicationUtils.getColor(materialDialog!!.getContext(), R.color.blue))
            materialDialog!!.getActionButton(DialogAction.NEGATIVE).setTextSize(size)
            materialDialog!!.getActionButton(DialogAction.NEGATIVE)
                .setTextColor(ApplicationUtils.getColor(materialDialog!!.getContext(), R.color.blue))
            materialDialog!!.getActionButton(DialogAction.NEUTRAL).setTextSize(size)
            materialDialog!!.getActionButton(DialogAction.NEUTRAL)
                .setTextColor(ApplicationUtils.getColor(materialDialog!!.getContext(), R.color.blue))
            materialDialog!!.getContentView()?.setTextSize(size)
            size = ApplicationUtils.px2sp(
                materialDialog!!.getContext(),
                materialDialog!!.getContext().getResources().getDimension(R.dimen.text_size_xlarge)
            )
            materialDialog!!.getTitleView().setTextSize(size)
            materialDialog!!.show()
        }
    }

    companion object {

        val NO_BUTTON = -1
        val ID = "id"
        val BUTTON = "button"
        val POSITIVE = "positive"
        val NEGATIVE = "negative"
        val NEUTRAL = "neutral"
    }

}
