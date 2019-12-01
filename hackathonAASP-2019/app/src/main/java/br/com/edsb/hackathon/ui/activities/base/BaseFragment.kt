package br.com.edsb.hackathon.ui.activities.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import br.com.edsb.hackathon.R
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.ItemListener
import com.afollestad.materialdialogs.list.listItems
import com.github.sundeepk.compactcalendarview.AnimationListener
import kotlinx.android.synthetic.main.image_dialog.*

open class BaseFragment : Fragment(), BaseFragmentInterfaces.View {
    private var context: Context? = null
    private var dialog: Dialog? = null
    private var imageDialog: Dialog? = null
    private var recyclerDialog: Dialog? = null
    private var dialogListener: BaseInterfaces.DialogListener? = null
    private var imageDialogListener: BaseInterfaces.DialogListener? = null
    private var recyclerDialogListener: BaseInterfaces.DialogListener? = null
    private var themeId: Int = 0

    override fun getStringResource(resource: Int) = getString(resource)

    override fun getStringResource(resource: Int, vararg formatArgs: Any) = getString(resource,
            formatArgs)

    override fun showProgressDialog() {
        dialog = Dialog(activity!!, R.style.DialogTheme)
        val window = dialog?.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        dialog?.setContentView(R.layout.progress_dialog)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    override fun showImageDialog(title: String?, description: String?, image: Bitmap,
                                 positiveButton: String?, negativeButton: String?,
                                 positiveAction: (dialog: Dialog) -> Unit, negativeAction: (dialog: Dialog) -> Unit) {
        imageDialog = Dialog(activity!!, R.style.DialogTheme)
        val window = imageDialog?.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        imageDialog?.setContentView(R.layout.image_dialog)
        imageDialog?.setCanceledOnTouchOutside(true)
        imageDialog?.setCancelable(true)

        imageDialog?.tvTitle?.text = title
        imageDialog?.tvDescription?.text = description
        imageDialog?.ivImage?.setImageBitmap(image)
        imageDialog?.btnPositive?.text = positiveButton
        imageDialog?.btnNegative?.text = negativeButton

        imageDialog?.btnPositive?.setOnClickListener {
            positiveAction(imageDialog!!)
        }

        imageDialog?.btnNegative?.setOnClickListener {
            negativeAction(imageDialog!!)
        }

        imageDialog?.btnClose?.setOnClickListener {
            imageDialog?.dismiss()
        }

        imageDialog?.show()
    }

    override fun showRecyclerDialog(title: String?, adapter: RecyclerView.Adapter<*>) {
        recyclerDialog = Dialog(activity!!, R.style.DialogTheme)
        val window = recyclerDialog?.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.CENTER)
        recyclerDialog?.setContentView(R.layout.recycler_dialog)
        recyclerDialog?.setCanceledOnTouchOutside(true)
        recyclerDialog?.setCancelable(true)

        recyclerDialog?.tvTitle?.text = title

        recyclerDialog?.btnClose?.setOnClickListener {
            recyclerDialog?.dismiss()
        }

        val rv = recyclerDialog?.findViewById<RecyclerView>(R.id.rvPopUp)
        rv?.layoutManager = LinearLayoutManager(context)
        rv?.adapter = adapter

        recyclerDialog?.show()
    }

    override fun dismissRecyclerDialog() {
        if (imageDialogListener != null)
            imageDialogListener?.systemDismiss()
        recyclerDialog?.dismiss()
    }

    override fun dismissImageDialog() {
        if (imageDialogListener != null)
            imageDialogListener?.systemDismiss()
        imageDialog?.dismiss()
    }

    override fun dismissProgressDialog() {
        if (dialogListener != null)
            dialogListener?.systemDismiss()
        dialog?.dismiss()
    }

    override fun setContext(context: Context) {
        this.context = context
    }


    override fun setProgressDialogText(text: String) {
        if (dialog != null)
            dialog!!.findViewById<TextView>(R.id.tvDialogText).text = text
    }

    override fun showWarningDialog(title: String, message: String) =
            MaterialDialog(activity!!)
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
        val dialog = MaterialDialog(activity!!)
                .title(text = title)
                .message(text = message)
                .positiveButton(text = positiveButton, click = positiveClick)
                .negativeButton(text = negativeButton, click = negativeClick)

        if (dismissListener != null)
            dialog.setOnDismissListener(dismissListener)

        dialog.show()
    }

    override fun showOptionsDialog(title: String, items: List<String>, itemListener: ItemListener) {
        MaterialDialog(activity!!)
                .title(text = title)
                .show {
                    listItems(items = items, selection = itemListener)
                }
    }

    override fun getContext(): Context {
        return when {
            activity!!.baseContext != null -> activity!!.baseContext
            context != null -> context!!
            activity!!.applicationContext != null -> activity!!.applicationContext
            else -> context!!
        }
    }


    override fun showSnack(parentView: View, message: String, duration: Int) {
        Snackbar.make(parentView, message, duration).show()
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
        MaterialDialog(activity!!).show {
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

    override fun getFadeInAnimation() = AnimationUtils.loadAnimation(activity!!, android.R.anim.fade_in)

    override fun getFadeOutAnimation(onAnimationEnd: () -> Unit): Animation {
        val anim = AnimationUtils.loadAnimation(activity!!, android.R.anim.fade_out)
        anim.setAnimationListener(object : AnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                onAnimationEnd()
            }
        })

        return anim
    }

    override fun getFadeInAnimation(onAnimationEnd: () -> Unit): Animation {
        val anim = AnimationUtils.loadAnimation(activity!!, android.R.anim.fade_in)
        anim.setAnimationListener(object : AnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                onAnimationEnd()
            }
        })

        return anim
    }

    override fun getSwipeRefreshColors(): IntArray = intArrayOf(
            ContextCompat.getColor(activity!!, R.color.colorAccent),
            ContextCompat.getColor(activity!!, R.color.gradient4),
            ContextCompat.getColor(activity!!, R.color.gradient2),
            ContextCompat.getColor(activity!!, R.color.gradient3),
            ContextCompat.getColor(activity!!, R.color.gradient1),
            ContextCompat.getColor(activity!!, R.color.gradient5),
            ContextCompat.getColor(activity!!, R.color.gradient6))
}