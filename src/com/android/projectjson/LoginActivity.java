package com.android.projectjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.projectjson.functions.UserFunctions;

public class LoginActivity extends Activity {

	EditText enama, epwd;
	Intent in;
	Button a_login;
	private ProgressDialog pDialog;
	UserFunctions uf;
	JSONParser jParser = new JSONParser();
	private static String url_login = "http://sipas.sekawanmedia.com/server/index.php/suratmasuk/select";
	private static final String TAG_DATA = "data";
	private static final String TAG_SM_ID = "sm_id";
	private static final String TAG_TANGGAL = "sm_tanggal";
	private static final String TAG_AGENDA = "sm_nomor_agenda";
	private static final String TAG_PERIHAL = "sm_perihal";
	private static boolean StatusLogin = false;
	// products JSONArray
	JSONArray data = null;
	ArrayList<HashMap<String, String>> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		enama = (EditText) findViewById(R.id.editUsername);
		epwd = (EditText) findViewById(R.id.editPass);
		a_login = (Button) findViewById(R.id.button_login);

		// cek status login
		StatusLogin = uf.isUserLoggedIn(getApplicationContext());
		if (StatusLogin == true) {
			in = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(in);
			finish();
		}
	}

	// tombol login
	public void tomboLogin(View v) {
		final String u = enama.getText().toString();
		final String p = epwd.getText().toString();

		if (u.length() == 0) {
			Toast.makeText(LoginActivity.this, "Username harap diisi",
					Toast.LENGTH_SHORT).show();

		} else if (p.length() == 0) {
			Toast.makeText(LoginActivity.this, "Password harap diisi",
					Toast.LENGTH_SHORT).show();
		} else {
			new prosesLogin().execute(u, p);
		}
	}

	// proses login
	public class prosesLogin extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Logging In...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Building Parameters
			// List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			// JSONObject json = jParser.makeHttpRequest(url_login, "GET",
			// params);
			String uname = (String) args[0];
			String pwd = (String) args[1];
			uf = new UserFunctions();
			JSONObject json = uf.loginUser(uname, pwd);
			// Check your log cat for JSON reponse
			Log.d("All Data: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				// int success = json.getInt(TAG_SUCCESS);
				// data = json.getJSONArray(TAG_DATA);
				DatabaseHandler db = new DatabaseHandler(
						getApplicationContext());
				// looping through All Products
				JSONObject c = json.getJSONObject("user");
				uf.logoutUser(getApplicationContext());

				// Storing string json to sqlite
				db.addUser(c.getString(TAG_SM_ID));

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
		}

	}
}
