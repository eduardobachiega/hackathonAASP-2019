package br.com.edsb.hackathon.ui.activities.base

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import br.com.edsb.hackathon.utils.CustomTarget
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.list.ItemListener

interface BaseInterfaces {
    interface View {
        fun showProgressDialog()
        fun showManualCodeButton()
        fun setManualCodeClickListener(onClick: () -> Unit)
        fun setProgressDialogText(text: String)
        fun dismissProgressDialog()
        fun setOnDialogDismissListener(dialogListener: DialogListener)
        fun showWarningDialog(title: String, message: String)
        fun showConfirmDialog(title: String, message: String, positiveButton: String,
                              negativeButton: String, positiveClick: DialogCallback,
                              negativeClick: DialogCallback)
        fun showConfirmDialog(title: String, message: String, positiveButton: String,
                              negativeButton: String, positiveClick: DialogCallback,
                              negativeClick: DialogCallback,
                              dismissListener: DialogInterface.OnDismissListener)
        fun getContext(): Context
        fun finish()
        fun getActivity(): Activity
        fun showTutorial(targetResource: Int, titleResource: Int, contentTextResource: Int,
                         position: CustomTarget.Position, clickListener: BaseActivity.OnClickListener)
        fun showSnack(parentView: android.view.View, message: String, duration: Int)
        fun getParentView(): android.view.View
        fun getFab(): android.view.View
        fun getStringResource(resource: Int): String
        fun getStringResource(resource: Int, vararg formatArgs: Any): String
        fun showOptionsDialog(title: String, items: List<String>, itemListener: ItemListener)
        fun showInputDialog(title: String,
                            message: String,
                            inputHint: String,
                            inputType: Int,
                            inputMaxLength: Int,
                            positiveButton: String,
                            negativeButton: String,
                            positiveClick: DialogCallback,
                            negativeClick: DialogCallback,
                            inputCallback: InputCallback)
    }

    interface DialogListener {
        fun systemDismiss()
        fun userDismiss()
    }
}