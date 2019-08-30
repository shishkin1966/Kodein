package shishkin.sl.kodeinpsb.sl.data

class Error {
    private val mErrorText = StringBuilder();
    private var mSender: String? = null;

    fun getErrorText(): String? {
        return if (mErrorText.length == 0) {
            null
        } else mErrorText.toString();
    }

    fun addError(sender: String, error: String?): Error {
        mSender = sender;
        addError(error);
        return this;
    }

    private fun addError(error: String?) {
        if (error == null) return

        if (!error.isNullOrEmpty()) {
            if (mErrorText.length > 0) {
                mErrorText.append("\n")
            }
            mErrorText.append(error)
        }
    }

    fun addError(sender: String, e: Exception?): Error {
        if (e != null) {
            mSender = sender
            addError(e.message)
        }
        return this
    }

    fun hasError(): Boolean {
        return mErrorText.length > 0
    }

    fun getSender(): String? {
        return mSender
    }

    fun setSender(sender: String): Error {
        mSender = sender
        return this
    }

}