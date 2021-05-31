package team.getherfolg.capstone.data.storage

import android.annotation.SuppressLint
import android.content.Context
import team.getherfolg.capstone.data.User

class SharedPrefManager private constructor(private val context: Context) {
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: User
        get() {
            val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPref.getInt("id", -1),
                sharedPref.getString("fullName", null),
                sharedPref.getString("email", null),
                sharedPref.getString("password", null)
            )
        }

    fun saveUser(user: User) {
        val sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()

        editor.putInt("id", user.id)
        editor.putString("fullName", user.fullName)
        editor.putString("username", user.username)
        editor.putString("email", user.email)

        editor.apply()
    }

    fun clear() {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "user_shared_pref"

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mContext: Context): SharedPrefManager {
            if (instance == null) {
                instance = SharedPrefManager(mContext)
            }
            return instance as SharedPrefManager
        }
    }
}