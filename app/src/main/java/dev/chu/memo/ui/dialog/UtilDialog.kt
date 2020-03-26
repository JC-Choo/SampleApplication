package dev.chu.memo.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.Window
import dev.chu.memo.R
import dev.chu.memo.etc.extension.getColorById
import kotlinx.android.synthetic.main.dialog_loading_progress_server.view.*

class UtilDialog {

    companion object {
        private var mInstance: UtilDialog? = null
        val instance: UtilDialog
            get() {
                if (mInstance == null)
                    mInstance =
                        UtilDialog()

                return mInstance!!
            }
    }

    private var customDialog: Dialog? = null
    private var hdAutoClose: Handler? = null

    @Synchronized
    fun showCustomProgressDialogAboutServer(context: Context?) {
        if (customDialog == null) {
            customDialog = Dialog(context!!)
            customDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

            customDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val inflater = LayoutInflater.from(context)
            val dialog = inflater.inflate(R.layout.dialog_loading_progress_server, null)

            dialog.loading_pb_circle.indeterminateDrawable.setColorFilter(context.getColorById(R.color.color_4A4A4A), android.graphics.PorterDuff.Mode.MULTIPLY)

            customDialog!!.setContentView(dialog)
        }

        if (context == null) {
            return
        }

        if (!(context as Activity).isFinishing) {
            customDialog!!.setCancelable(false)
            customDialog!!.show()
        }
    }

    @Synchronized
    fun closeCustomProgressDialog() {
        try {
            if (hdAutoClose != null)
                hdAutoClose!!.removeMessages(1001)

            if (customDialog != null) {
                if (customDialog!!.isShowing)
                    customDialog!!.dismiss()
                customDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
