package com.android.projectjson.functions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.android.projectjson.DatabaseHandler;
import com.android.projectjson.JSONParser;

import android.content.Context;

public class UserFunctions {
	private JSONParser jsonParser;

	// Testing in localhost using wamp or xampp
	// use http://10.0.2.2/ to connect to your localhost ie http://localhost/
	// private static String urlIndex =
	// "http://192.168.1.11/MalangFoodDelivery/android/";
	private static String urlIndex = new Koneksi().isi_koneksi();
	private static String url_login = "http://sipas.sekawanmedia.com/server/index.php/suratmasuk/select";
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String update_tag = "mengedit";
	private static String sesi_tag = "ambilsesi";
	private static String jam_tag = "cekjam";
	private static String cart_tag = "cartdetail";
	private static String delcart_tag = "deletecartdetail";
	private static String updcart_tag = "updatecartdetail";
	private static String total_tag = "total";
	private static String order_tag = "order";

	// constructor
	public UserFunctions() {
		jsonParser = new JSONParser();
	}

	/**
	 * function untuk Login Request
	 * 
	 * @param username
	 * @param password
	 **/

	public JSONObject ambilSesi() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", sesi_tag));
		JSONObject json = jsonParser.makeHttpRequest(urlIndex, "GET", params);
		return json;
	}

	// Fungsi Login
	public JSONObject loginUser(String username, String password) {

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.makeHttpRequest(url_login, "GET", params);
		// return json
		// Log.e("JSON", json.toString());
		return json;

	}

	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

	/**
	 * Function to logout user Reset Database
	 * */
	public boolean logoutUser(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}

	public boolean cekSesi(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCountSesi();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

	public boolean gantiSesi(Context context) {
		DatabaseHandler dbsesi = new DatabaseHandler(context);
		dbsesi.resetSesi();
		return true;
	}

}
