package com.jianjun.base.utils

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.jianjun.base.R
import java.lang.ref.WeakReference

/**
 * Created by jianjunhuang on 2/14/20.
 */
class SnackbarUtils {
    constructor() {
        throw UnsupportedOperationException("can't instantiate it")
    }

    companion object {
        private var weakReference: WeakReference<Snackbar>? = null
        private fun showSnackbar(
            parent: View,
            text: CharSequence,
            duration: Int,
            textColor: Int,
            bgColor: Int,
            actionText: CharSequence?,
            actionTextColor: Int,
            listener: View.OnClickListener?
        ) {
            when (duration) {
                Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG -> SnackbarUtils.weakReference =
                    WeakReference(Snackbar.make(parent, text, duration))
                Snackbar.LENGTH_INDEFINITE -> SnackbarUtils.weakReference =
                    WeakReference(
                        Snackbar.make(
                            parent,
                            text,
                            Snackbar.LENGTH_INDEFINITE
                        ).setDuration(duration)
                    )
                else -> SnackbarUtils.weakReference =
                    WeakReference(Snackbar.make(parent, text, duration))
            }
            val view: View? =
                SnackbarUtils.weakReference?.get()?.getView()
            (view?.findViewById<View>(R.id.snackbar_text) as TextView).setTextColor(
                textColor
            )
            view.setBackgroundColor(bgColor)
            if (actionText != null && actionText.length > 0 && listener != null) {
                SnackbarUtils.weakReference?.get()?.setActionTextColor(actionTextColor)
                SnackbarUtils.weakReference?.get()?.setAction(actionText, listener)
            }
            SnackbarUtils.weakReference?.get()?.show()
        }
    }
}