package br.com.edsb.hackathon.data.preferences

import br.com.edsb.hackathon.application.BaseApplication

class AppPreferences {
    enum class PreferencesNames(val value: String) {
        LOGGED("LOGGED"),
        USERNAME("USERNAME"),
    }

    companion object {
        fun registerUserLogin() = BaseApplication.setBoolPreference(PreferencesNames.LOGGED.value,
                true)

        fun isUserLogged() = BaseApplication.getBoolPreference(PreferencesNames.LOGGED.value,
                false)

        fun deletePreferences() {
            BaseApplication.deletePreference(PreferencesNames.LOGGED.value)
        }

        fun registerUserName(userName: String) =
                BaseApplication.setPreference(PreferencesNames.USERNAME.value, userName)

        fun getUserName() = BaseApplication.getPreference(PreferencesNames.USERNAME.value)
    }
}