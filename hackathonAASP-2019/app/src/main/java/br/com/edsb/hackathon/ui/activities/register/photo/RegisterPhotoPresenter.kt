package br.com.edsb.hackathon.ui.activities.register.photo

import android.graphics.Bitmap
import android.net.Uri
import br.com.edsb.hackathon.R
import java.io.ByteArrayOutputStream

class RegisterPhotoPresenter(private val view: RegisterPhotoInterfaces.View): RegisterPhotoInterfaces.Presenter {
    private val model = RegisterPhotoModel(this)


    override fun uploadPhoto(bitmap: Bitmap?) {
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        view.showProgressDialog()
        view.setProgressDialogText(view.getStringResource(R.string.uploading))
        model.uploadFileToFirebase(data)
    }

    override fun photoUploadSuccessful() {
        view.dismissProgressDialog()
        view.proceedToMenu()
    }

    override fun photoUploadUnsuccessful() {
        view.dismissProgressDialog()
        view.showConfirmDialog(view.getStringResource(R.string.attention),
                view.getStringResource(R.string.upload_error),
                view.getStringResource(R.string.proceed),
                view.getStringResource(R.string.try_again),
                {
                    view.proceedToMenu()
                },
                {

                })
    }

    override fun receivePhotoUrl(uri: Uri) = setImage(uri)

    private fun setImage(uri: Uri) = view.setImage(uri)

    override fun loadPhoto() = model.getUserPhotoUrl()


}