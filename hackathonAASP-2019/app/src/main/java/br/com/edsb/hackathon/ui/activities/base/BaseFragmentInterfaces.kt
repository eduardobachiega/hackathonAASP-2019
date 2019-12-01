package br.com.edsb.hackathon.ui.activities.base

import android.app.Activity
import android.app.Dialog
import android.content.AbstractThreadedSyncAdapter
import android.content.ClipDescription
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.animation.Animation
import br.com.edsb.hackathon.utils.CustomTarget
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.list.ItemListener

interface BaseFragmentInterfaces {
    interface View {
        fun showProgressDialog()
        fun setProgressDialogText(text: String)
        fun dismissProgressDialog()
        fun showWarningDialog(title: String, message: String)
        fun showConfirmDialog(title: String, message: String, positiveButton: String,
                              negativeButton: String, positiveClick: DialogCallback,
                              negativeClick: DialogCallback)

        fun showConfirmDialog(title: String, message: String, positiveButton: String,
                              negativeButton: String, positiveClick: DialogCallback,
                              negativeClick: DialogCallback,
                              dismissListener: DialogInterface.OnDismissListener)

        fun getContext(): Context
        fun showSnack(parentView: android.view.View, message: String, duration: Int)
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

        fun getFadeInAnimation(): Animation
        fun getFadeInAnimation(onAnimationEnd: () -> Unit): Animation
        fun getFadeOutAnimation(onAnimationEnd: () -> Unit): Animation
        fun showImageDialog(title: String?, description: String?, image: Bitmap,
                            positiveButton: String?, negativeButton: String?,
                            positiveAction: (dialog: Dialog) -> Unit, negativeAction: (dialog: Dialog) -> Unit)

        fun dismissImageDialog()
        fun getSwipeRefreshColors(): IntArray
        fun setContext(context: Context)
        fun showRecyclerDialog(title: String?, adapter: RecyclerView.Adapter<*>)
        fun dismissRecyclerDialog()
    }
}