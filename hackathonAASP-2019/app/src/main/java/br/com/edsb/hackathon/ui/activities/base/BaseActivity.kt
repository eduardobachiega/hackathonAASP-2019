package br.com.edsb.hackathon.ui.activities.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.TextView
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.utils.CustomTarget
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.ItemListener
import com.afollestad.materialdialogs.list.listItems
import com.github.amlcurran.showcaseview.ShowcaseView

open class BaseActivity: AppCompatActivity(), BaseInterfaces.View {

    private val context = this
    private var dialog: Dialog? = null
    var dialogListener: BaseInterfaces.DialogListener? = null
    private var themeId: Int = 0

    override fun getParentView() = findViewById<View>(android.R.id.content)

    override fun getFab() = findViewById<View>(R.id.fab)

    override fun setTheme(resid: Int) {
        super.setTheme(resid)
        themeId = resid
    }

    override fun getStringResource(resource: Int) = getString(resource)

    override fun getStringResource(resource: Int, vararg formatArgs: Any) = getString(resource,
            formatArgs)

    override fun showProgressDialog() {
        dialog = Dialog(context, R.style.DialogTheme)
        val window = dialog?.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        dialog?.setContentView(R.layout.progress_dialog)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    override fun showManualCodeButton() {
        val tvManualCode = dialog?.findViewById<TextView>(R.id.tvManualCode)
        tvManualCode?.visibility = VISIBLE
    }

    override fun setManualCodeClickListener(onClick: () -> Unit) {
        val tvManualCode = dialog?.findViewById<TextView>(R.id.tvManualCode)
        tvManualCode?.setOnClickListener {
            onClick()
        }
    }

    override fun dismissProgressDialog() {
        if (dialogListener != null)
            dialogListener?.systemDismiss()
        dialog?.dismiss()
    }

    override fun setProgressDialogText(text: String) {
        if (dialog != null)
            dialog!!.findViewById<TextView>(R.id.tvDialogText).text = text
    }

    override fun setOnDialogDismissListener(dialogListener: BaseInterfaces.DialogListener) {
        this.dialogListener = dialogListener
    }

    override fun showWarningDialog(title: String, message: String) =
            MaterialDialog(this)
                    .title(text = title)
                    .message(text = message)
                    .positiveButton(R.string.ok) { }
                    .show()

    override fun showConfirmDialog(title: String, message: String, positiveButton: String,
                                   negativeButton: String, positiveClick: DialogCallback,
                                   negativeClick: DialogCallback) {
        showConfirmCancelDialog(title, message, positiveButton, negativeButton, positiveClick,
                negativeClick, null)
    }

    override fun showConfirmDialog(title: String, message: String, positiveButton: String,
                                   negativeButton: String, positiveClick: DialogCallback,
                                   negativeClick: DialogCallback,
                                   dismissListener: DialogInterface.OnDismissListener) {
        showConfirmCancelDialog(title, message, positiveButton, negativeButton, positiveClick,
                negativeClick, dismissListener)
    }

    private fun showConfirmCancelDialog(title: String, message: String, positiveButton: String,
                                        negativeButton: String, positiveClick: DialogCallback,
                                        negativeClick: DialogCallback,
                                        dismissListener: DialogInterface.OnDismissListener?) {
        val dialog = MaterialDialog(this)
                .title(text = title)
                .message(text = message)
                .positiveButton(text = positiveButton, click = positiveClick)
                .negativeButton(text = negativeButton, click = negativeClick)

        if (dismissListener != null)
            dialog.setOnDismissListener(dismissListener)

        dialog.show()
    }

    override fun showOptionsDialog(title: String, items: List<String>, itemListener: ItemListener) {
        MaterialDialog(this)
                .title(text = title)
                .show {
                    listItems(items = items, selection = itemListener)
                }
    }

    override fun showInputDialog(title: String,
                                 message: String,
                                 inputHint: String,
                                 inputType: Int,
                                 inputMaxLength: Int,
                                 positiveButton: String,
                                 negativeButton: String,
                                 positiveClick: DialogCallback,
                                 negativeClick: DialogCallback,
                                 inputCallback: InputCallback) {
        MaterialDialog(this).show {
            title(text = title)
            message(text = message)
            input(hint = inputHint,
                    waitForPositiveButton = false,
                    inputType = inputType,
                    maxLength = inputMaxLength,
                    callback = inputCallback)
            positiveButton(text = positiveButton, click = positiveClick)
            negativeButton(text = negativeButton, click = negativeClick)
        }
    }

    override fun getContext(): Context {
        return context
    }

    override fun showTutorial(targetResource: Int, titleResource: Int, contentTextResource: Int,
                              position: CustomTarget.Position, clickListener: OnClickListener) {
        BaseActivity.mItemClickListener = clickListener

        val builder = ShowcaseView.Builder(this)
                .setTarget(CustomTarget(findViewById(targetResource), position))
                .setContentTitle(titleResource)
                .setContentText(contentTextResource)
                .withNewStyleShowcase()
                .setStyle(R.style.CustomShowcaseTheme)

        val showcase = builder.build()

        builder.setOnClickListener {
            clickListener.onItemClick(showcase)
        }
    }

    override fun getActivity() = this as Activity

    interface OnClickListener {
        fun onItemClick(showCaseView: ShowcaseView)
    }

    fun setOnClickListener(mItemClickListener: OnClickListener) {
        BaseActivity.mItemClickListener = mItemClickListener
    }

    override fun showSnack(parentView: View, message: String, duration: Int) {
        Snackbar.make(parentView, message, duration).show()
    }

    companion object {
        internal var mItemClickListener: OnClickListener? = null
    }
}