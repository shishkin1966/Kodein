package shishkin.sl.kodeinpsb.app.screen.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment


class ContactFragment : AbsContentFragment() {
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

    override fun getName(): String {
        return NAME
    }

}

