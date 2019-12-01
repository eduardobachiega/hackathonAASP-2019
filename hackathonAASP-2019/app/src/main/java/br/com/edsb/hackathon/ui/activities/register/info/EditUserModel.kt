package br.com.edsb.hackathon.ui.activities.register.info

import br.com.edsb.hackathon.BuildConfig
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.data.preferences.AppPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class EditUserModel(private val presenter: EditUserInterfaces.Presenter) : EditUserInterfaces.Model {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference.child(BuildConfig.FIREBASE_DATABASE)

    override fun registerUserData(email: String, name: String, lastName: String, firstTime: Boolean): Boolean = try {
        val user = FirebaseAuth.getInstance().currentUser
        val userReference = databaseReference.child("users")
                .child(UserFirebaseHelper.getId())

        userReference.child("email").setValue(email)
        userReference.child("name").setValue(name)
        userReference.child("lastName").setValue(lastName)
        userReference.child("phone").setValue(user?.phoneNumber)

        registerPreferences(name, lastName)

        true
    } catch (e: Exception) {
        false
    }

    override fun getUser(userCallback: UserFirebaseHelper.Companion.UserSearchCallback) =
            UserFirebaseHelper.getUser(userCallback)

    override fun registerPreferences(name: String, lastName: String) {
        AppPreferences.registerUserName("$name $lastName")
        AppPreferences.registerUserLogin()
    }
}