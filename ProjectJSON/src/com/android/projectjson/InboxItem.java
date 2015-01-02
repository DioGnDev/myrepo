package com.android.projectjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InboxItem extends Fragment {

	private static final String TAG_DATA = "data";
	private static final String TAG_SM_ID = "sm_id";
	private static final String TAG_TANGGAL = "sm_tanggal";
	private static final String TAG_AGENDA = "sm_nomor_agenda";
	private static final String TAG_PERIHAL = "sm_perihal";
	// url to get all products list
	private static String url_all_products = "http://sipas.sekawanmedia.com/server/index.php/suratmasuk/select";

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
	Customlist cadapter;
	
	// products JSONArray
	
	JSONArray data = null;
	ListView lv;

	public InboxItem() {

	}

	
	  @Override 
	  public View onCreateView(LayoutInflater inflater, ViewGroup
	  container, Bundle savedInstanceState) {
	  
	  View rootView = inflater.inflate(R.layout.fragment_allitem, container,
	  false);
	  
	  // new LoadAllProducts().execute(); dataList = new
	  
	  lv = (ListView)rootView.findViewById(R.id.list_inbox);
	  new LoadAllProducts().execute();
			cadapter = new Customlist(getActivity(), dataList);
	  return rootView;
	  
	  }
	 

	/*@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*
		 * String[] values = new String[] { "Android", "iPhone",
		 * "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7",
		 * "Max OS X", "Linux", "OS/2" }; ArrayAdapter<String> adapter = new
		 * ArrayAdapter<String>(getActivity(),
		 * android.R.layout.simple_list_item_1, values);
		 * setListAdapter(adapter);
		 
		lv = 
		new LoadAllProducts().execute();
		adapter = new Customlist(getActivity(), dataList);

	}*/

	

	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading message. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
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
			
			lv.setAdapter(cadapter);
			
			
			/*dataList = new ArrayList<HashMap<String, String>>();
			String[] values = new String[] { "Android", "iPhone",
					"WindowsMobile", "Blackberry", "WebOS", "Ubuntu",
					"Windows7", "Max OS X", "Linux", "OS/2" };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1, values);

			setListAdapter(adapter);

			/*
			 * ListAdapter adapter = new SimpleAdapter(getActivity(), dataList,
			 * R.layout.listrow, new String[] { TAG_SM_ID, TAG_TANGGAL,
			 * TAG_AGENDA, TAG_PERIHAL }, new int[] { R.id.nim, R.id.nama,
			 * R.id.prodi, R.id.fakultas });
			 * 
			 * lv.setAdapter(adapter);
			 */
		}

	}

}
