package br.com.edsb.hackathon.ui.activities.register.photo

import br.com.edsb.hackathon.BuildConfig
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import com.google.firebase.storage.FirebaseStorage

class RegisterPhotoModel(private val presenter: RegisterPhotoInterfaces.Presenter) : RegisterPhotoInterfaces.Model {
    private val storageRef = FirebaseStorage.getInstance().reference.child(BuildConfig.FIREBASE_DATABASE)

    override fun uploadFileToFirebase(byteArray: ByteArray) {
        val uploadTask = storageRef
                .child("profilePictures/${UserFirebaseHelper.getId()}.jpg")
                .putBytes(byteArray)
        uploadTask.addOnFailureListener {
            presenter.photoUploadUnsuccessful()
        }.addOnSuccessListener {
            presenter.photoUploadSuccessful()
        }
    }

    override fun getUserPhotoUrl() {
        UserFirebaseHelper.getProfilePictureDownloadURL().addOnSuccessListener {
            presenter.receivePhotoUrl(it)
        }
    }

}