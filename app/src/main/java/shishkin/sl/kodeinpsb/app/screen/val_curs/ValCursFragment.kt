package shishkin.sl.kodeinpsb.app.screen.val_curs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shishkin.sl.kodeinpsb.R
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.ui.AbsContentFragment


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
