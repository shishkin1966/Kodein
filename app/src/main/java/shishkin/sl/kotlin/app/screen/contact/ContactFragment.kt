package shishkin.sl.kotlin.app.screen.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.sl.action.ApplicationAction
import shishkin.sl.kotlin.sl.model.IModel
import shishkin.sl.kotlin.sl.ui.AbsContentFragment


class ContactFragment : AbsContentFragment(), View.OnClickListener {
    companion object {
        const val NAME = "ContactFragment"

        fun newInstance(): ContactFragment {
            return ContactFragment()
        }
    }

    override fun createModel(): IModel {
        return ContactModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.web).setOnClickListener(this)
        view.findViewById<View>(R.id.mail).setOnClickListener(this)
    }

    override fun getName(): String {
        return NAME
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.web -> {
                getModel<ContactModel>().getPresenter<ContactPresenter>().addAction(
                    ApplicationAction(ContactPresenter.WebAction)
                )
            }
            R.id.mail -> {
                getModel<ContactModel>().getPresenter<ContactPresenter>().addAction(
                    ApplicationAction(ContactPresenter.MailAction)
                )
            }
        }
    }

}

