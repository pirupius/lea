package com.agritech.lea.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.agritech.lea.accounts.LoginActivity;

import java.util.HashMap;

public class SessionManager {

	SharedPreferences pref;
	Editor editor;
	Context _context;
	
	int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Lea";

	private static final String IS_LOGIN = "IsLoggedIn";
	
	public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_DISTRICT = "district";
    public static final String KEY_LOCATION = "location";
	
	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(
	        String id, String name, String gender, String phone, String email, String district, String location
    ){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		editor.putString(KEY_ID, id);
		editor.putString(KEY_NAME, name);
		editor.putString(KEY_GENDER, gender);
		editor.putString(KEY_PHONE, phone);
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_DISTRICT, district);
		editor.putString(KEY_LOCATION, location);

		editor.commit();
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);

			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}
		
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();

		user.put(KEY_ID, pref.getString(KEY_ID, null));
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_DISTRICT, pref.getString(KEY_DISTRICT, null));
        user.put(KEY_LOCATION, pref.getString(KEY_LOCATION, null));

		// return user
		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
	}
	
	/**
	 * Quick check for login
     **/
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
