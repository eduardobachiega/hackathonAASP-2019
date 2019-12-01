package br.com.edsb.hackathon.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class BaseApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("SYSTEM_PREFERENCES", Context.MODE_PRIVATE)
    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext() = instance!!.applicationContext

        private var preferences: SharedPreferences? = null

        fun getPreference(preferenceName: String) = preferences
                ?.getString(preferenceName, "")

        fun getIntPreference(preferenceName: String) = preferences?.getInt(preferenceName,
                0)

        fun getLongPreference(preferenceName: String) = preferences?.getLong(preferenceName,
                0)

        fun getBoolPreference(preferenceName: String) = getBoolPreference(preferenceName, false)

        fun getBoolPreference(preferenceName: String, defaultValue: Boolean) = preferences
                ?.getBoolean(preferenceName, defaultValue)

        fun setPreference(preferenceName: String, value: String) = preferences?.edit()
                ?.putString(preferenceName, value)?.commit()

        fun setIntPreference(preferenceName: String, value: Int) = preferences?.edit()
                ?.putInt(preferenceName, value)?.commit()

        fun setLongPreference(preferenceName: String, value: Long) = preferences?.edit()
                ?.putLong(preferenceName, value)?.commit()

        fun setBoolPreference(preferenceName: String, value: Boolean) = preferences?.edit()
                ?.putBoolean(preferenceName, value)?.commit()

        fun deletePreference(preferenceName: String) = preferences?.edit()
                ?.remove(preferenceName)?.commit()
    }
}