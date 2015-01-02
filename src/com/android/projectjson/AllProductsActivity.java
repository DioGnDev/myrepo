package com.android.projectjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllProductsActivity extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> dataList;

	// url to get all products list
	private static String url_all_products = "http://sipas.sekawanmedia.com/server/index.php/suratmasuk/select";

	// JSON Node names
	/*
	 * private static final String TAG_PROFIL = "profil"; private static final
	 * String TAG_NIM = "nim"; private static final String TAG_NAMA= "nama";
	 * private static final String TAG_PRODI = "prodi"; private static final
	 * String TAG_FAKULTAS = "fakultas";
	 */

	private static final String TAG_DATA = "data";
	private static final String TAG_SM_ID = "sm_id";
	private static final String TAG_TANGGAL = "sm_tanggal";
	private static final String TAG_AGENDA = "sm_nomor_agenda";
	private static final String TAG_PERIHAL = "sm_perihal";

	// products JSONArray
	JSONArray data = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_products);

		// Hashmap for ListView
		dataList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		new LoadAllProducts().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single product
		// launching Edit Product Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});

	}

	// Response from Edit Product Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received
			// means user edited/deleted product
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AllProductsActivity.this);
			pDialog.setMessage("Loading message. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("All Data: ", json.toString());

			try {
				
				data = json.getJSONArray(TAG_DATA);

				// looping through All Products
				for (int i = 0; i < data.length(); i++) {

					JSONObject c = data.getJSONObject(i);

					// Storing each json item in variable

					String nim = c.getString(TAG_SM_ID);
					String nama = c.getString(TAG_TANGGAL);
					String prodi = c.getString(TAG_AGENDA);
					String fakultas = c.getString(TAG_PERIHAL);

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_SM_ID, nim);
					map.put(TAG_TANGGAL, nama);
					map.put(TAG_AGENDA, prodi);
					map.put(TAG_PERIHAL, fakultas);

					// adding HashList to ArrayList
					dataList.add(map);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							AllProductsActivity.this, dataList,
							R.layout.listrow, new String[] { TAG_SM_ID,
									TAG_TANGGAL, TAG_AGENDA, TAG_PERIHAL },
							new int[] { R.id.nim, R.id.nama, R.id.prodi,
									R.id.fakultas });

					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}