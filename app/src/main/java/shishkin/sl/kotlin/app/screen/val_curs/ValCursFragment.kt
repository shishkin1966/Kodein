package shishkin.sl.kotlin.app.screen.val_curs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shishkin.sl.kotlin.R
import shishkin.sl.kotlin.sl.model.IModel
import shishkin.sl.kotlin.sl.ui.AbsContentFragment


class ValCursFragment : AbsContentFragment() {
    companion object {
        const val NAME = "ValCursFragment"

        fun newInstance(): ValCursFragment {
            return ValCursFragment()
        }
    }

    override fun createModel(): IModel {
        return ValCursModel(this)
    }

    override fun getName(): String {
        return NAME
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_val_curs, container, false)
    }

}
