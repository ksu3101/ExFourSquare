package com.sw.exfoursquare.base.helper

import android.app.ProgressDialog
import android.graphics.drawable.ColorDrawable
import android.os.Looper
import android.view.LayoutInflater
import com.sw.exfoursquare.R
import com.sw.exfoursquare.base.di.currentActivity
import com.sw.model.base.helper.ProgressDialogHelper
import org.koin.core.KoinComponent

/**
 * @author burkd
 * @since 2019-11-14
 */
class ProgressDialogHelperImpl : ProgressDialogHelper, KoinComponent {
    private lateinit var progressDialog: ProgressDialog

    override fun show() {
        showProgressDialog()
    }

    override fun isProgress(): Boolean =
        (isProgressDialogInstanceInitialized() && progressDialog.isShowing)

    override fun hide() {
        if (isProgressDialogInstanceInitialized()) {
            progressDialog.dismiss()
        }
    }

    private fun showProgressDialog() {
        if (isProgress()) {
            return
        } else if (isProgressDialogInstanceInitialized()) {
            progressDialog.show()
        }
        val activity = getKoin().currentActivity()
        if (Looper.myLooper() == Looper.getMainLooper()) {
            val progressView = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null)
            this.progressDialog = ProgressDialog(activity)
            progressDialog.setContentView(progressView)
            progressDialog.setCancelable(false)
            progressDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            progressDialog.show()
        }
    }

    private fun isProgressDialogInstanceInitialized(): Boolean = (::progressDialog.isInitialized)

}