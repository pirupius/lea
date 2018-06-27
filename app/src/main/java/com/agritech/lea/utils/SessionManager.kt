package com.agritech.lea.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.agritech.lea.accounts.LoginActivity

import java.util.HashMap

class SessionManager(internal var _context: Context) {

    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor

    internal var PRIVATE_MODE = 0

    val userDetails: HashMap<String, String>
        get() {
            val user = HashMap<String, String>()
            user[KEY_ID] = pref.getString(KEY_ID, null)
            user[KEY_NAME] = pref.getString(KEY_NAME, null)
            user[KEY_GENDER] = pref.getString(KEY_GENDER, null)
            user[KEY_PHONE] = pref.getString(KEY_PHONE, null)
            user[KEY_EMAIL] = pref.getString(KEY_EMAIL, null)
            user[KEY_DISTRICT] = pref.getString(KEY_DISTRICT, null)
            user[KEY_LOCATION] = pref.getString(KEY_LOCATION, null)
            return user
        }

    val isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    //phone, email, district, location
    fun createLoginSession(name: String, gender: String, phone: String, email: String, district: String, location: String) {
        editor.putBoolean(KEY_ID, true)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_GENDER, gender)
        editor.putString(KEY_PHONE, phone)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_DISTRICT, district)
        editor.putString(KEY_LOCATION, location)

        editor.commit()
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    fun checkLogin() {
        // Check login status
        if (!this.isLoggedIn) {
            // user is not logged in redirect him to Login Activity
            val i = Intent(_context, LoginActivity::class.java)
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // Add new Flag to start new Activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Staring Login Activity
            _context.startActivity(i)
        }

    }

    fun logoutUser() {
        editor.clear()
        editor.commit()

        val i = Intent(_context, LoginActivity::class.java)

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        _context.startActivity(i)
    }

    companion object {

        private val PREF_NAME = "Lea"
        private val IS_LOGIN = "IsLoggedIn"

        val KEY_ID = "id"
        val KEY_NAME = "name"
        val KEY_GENDER = "gender"
        val KEY_PHONE = "phone"
        val KEY_EMAIL = "email"
        val KEY_DISTRICT = "district"
        val KEY_LOCATION = "location"

    }
}
