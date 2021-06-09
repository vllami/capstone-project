package team.getherfolg.capstone.ui.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    private val nameSharedPref = "auth_shared_pref"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(nameSharedPref, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun putDataBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getDataBoolean(key: String): Boolean = sharedPref.getBoolean(key, false)

    fun clear() {
        editor.clear()
            .apply()
    }


}