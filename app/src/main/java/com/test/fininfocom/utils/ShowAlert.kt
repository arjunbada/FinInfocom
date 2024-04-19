package com.test.fininfocom.utils

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.test.fininfocom.R

class ShowAlert (private val activity: Activity) {
    private var loadingDialog: Dialog? = null

    //show loading
    fun showLoading() {
        try {
            if (loadingDialog == null) {
                loadingDialog = Dialog(activity)
                loadingDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                loadingDialog!!.setCancelable(true)
                loadingDialog!!.setCanceledOnTouchOutside(true)
                loadingDialog!!.setContentView(R.layout.custom_loading)
                loadingDialog!!.window!!.setDimAmount(0f)

                //enable click behind dialog
                val window = loadingDialog!!.window
                if (window != null) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    )
                    window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                }
            }
            loadingDialog!!.show()

            //for dismissing
            loadingDialog!!.setOnCancelListener { dialog: DialogInterface? -> dialog!!.dismiss() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //dismiss loading
    fun dismissLoading() {
        try {
            if (loadingDialog != null && loadingDialog!!.isShowing) {
                loadingDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //show warning alert
    fun showWarningAlert(title: String?, content: String?) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(content)
            .setPositiveButton(android.R.string.ok) { dialog: DialogInterface, _: Int ->
                // Continue with delete operation
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    //show warning alert
    fun showWarningOptionAlert(title: String?, content: String?) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(content) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(R.string.yes) { dialog: DialogInterface?, _: Int -> dialog?.dismiss() } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(R.string.no) { dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    //show alert message
    fun showMessage(strMessage: String?) {
        val snackBar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            strMessage!!,
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction(
            activity.getString(R.string.dismiss)
        ) {
            /*snack bar dismiss*/
            snackBar.dismiss()
        }
        snackBar.show()
    }

    //show alert message
    fun showMessageAutoDismiss(strMessage: String?) {
        val snackBar = Snackbar.make(activity.findViewById(android.R.id.content),
            strMessage!!,
            Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    fun toastMessage(strMessage: String?) {
        Toast.makeText(activity, strMessage, Toast.LENGTH_LONG).show()
    }
}