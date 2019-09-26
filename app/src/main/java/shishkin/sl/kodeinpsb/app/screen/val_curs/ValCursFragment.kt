package shishkin.sl.kodeinpsb.app.screen.val_curs

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
}
