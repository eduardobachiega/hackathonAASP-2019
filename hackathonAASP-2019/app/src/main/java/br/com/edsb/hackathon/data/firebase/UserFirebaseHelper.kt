package br.com.edsb.hackathon.data.firebase

import android.util.Log
import br.com.edsb.hackathon.BuildConfig
import br.com.edsb.hackathon.data.model.User
import br.com.edsb.hackathon.utils.encoding.Digest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class UserFirebaseHelper {

    companion object {
        interface UserSearchCallback {
            fun onUserFound(user: User)
            fun onUserNotFound()
        }

        interface RegisterDocumentCallback {
            fun onFinished()
            fun onCanceled()
        }

        val firebaseAuth = FirebaseAuth.getInstance()!!
        val storage = FirebaseStorage.getInstance().reference.child(BuildConfig.FIREBASE_DATABASE)
        val database = FirebaseDatabase.getInstance().reference.child(BuildConfig.FIREBASE_DATABASE)

        fun logoutUser() = firebaseAuth.signOut()

        fun getUserUID() = firebaseAuth.currentUser?.uid

        fun getId() = getId(firebaseAuth.currentUser?.phoneNumber!!)

        fun getId(phoneNumber: String) = Digest().md5(phoneNumber, Digest.RETURN.HEX_STRING)
                as String


        fun getProfilePictureDownloadURL() = getProfilePictureDownloadURL(getId())

        fun getProfilePictureDownloadURL(userId: String) =
                storage.child("profilePictures/$userId.jpg").downloadUrl

        fun getDocumentPhotoURL(item: String) = getDocumentPhotoURL(item, getId())

        fun getDocumentPhotoURL(item: String, userId: String) =
                storage.child("documents/$userId/$item").downloadUrl

        fun getUser(userCallback: UserSearchCallback) = getUser(getId(), userCallback)

        fun getUser(userId: String, userCallback: UserSearchCallback) {
            database.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("DS", "$p0")
                    userCallback.onUserNotFound()
                }

                override fun onDataChange(ds: DataSnapshot) {
                    Log.e("DS", ds.toString())
                    if (ds.value != null) {
                        val user = User()
                        user.id = ds.key.toString()
                        user.name = ds.child("name").value.toString()
                        user.lastName = ds.child("lastName").value.toString()
                        user.email = ds.child("email").value.toString()
                        user.photo = getProfilePictureDownloadURL(user.id!!)


                        if (user.name == "null" && user.email == "null" && user.lastName == "null")
                            userCallback.onUserNotFound()
                        else
                            userCallback.onUserFound(user)
                    } else {
                        userCallback.onUserNotFound()
                    }
                }
            })
        }

        fun searchUser(phoneNumber: String, searchCallback: UserSearchCallback) =
                searchUserByID(getId(phoneNumber), searchCallback)

        fun searchUserByID(userId: String, searchCallback: UserSearchCallback) {
            database.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    searchCallback.onUserNotFound()
                }

                override fun onDataChange(ds: DataSnapshot) {
                    Log.e("DS", ds.toString())
                    if (ds.value != null) {
                        val user = User()
                        user.id = ds.key.toString()
                        user.name = ds.child("name").value.toString()
                        user.lastName = ds.child("lastName").value.toString()
                        user.email = ds.child("email").value.toString()
                        user.photo = getProfilePictureDownloadURL(user.id!!)

                        searchCallback.onUserFound(user)
                    } else {
                        searchCallback.onUserNotFound()
                    }
                }
            })
        }


    }
}