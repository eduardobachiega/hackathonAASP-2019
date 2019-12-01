package br.com.edsb.hackathon.ui.activities.register.photo

import android.graphics.Bitmap
import android.net.Uri
import br.com.edsb.hackathon.ui.activities.base.BaseInterfaces

interface RegisterPhotoInterfaces {
    interface Model{
        fun uploadFileToFirebase(byteArray: ByteArray)
        fun getUserPhotoUrl()
    }

    interface View: BaseInterfaces.View{
        fun proceedToMenu()
        fun setImage(uri: Uri)
    }

    interface Presenter{
        fun uploadPhoto(bitmap: Bitmap?)
        fun photoUploadSuccessful()
        fun photoUploadUnsuccessful()
        fun receivePhotoUrl(uri: Uri)
        fun loadPhoto()
    }
}