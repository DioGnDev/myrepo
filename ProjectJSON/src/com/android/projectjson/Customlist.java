package com.android.projectjson;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.projectjson.functions.Koneksi;

public class Customlist extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static final String TAG_SM_ID = "sm_id";
	private static final String TAG_TANGGAL = "sm_tanggal";
	private static final String TAG_AGENDA = "sm_nomor_agenda";
	private static final String TAG_PERIHAL = "sm_perihal";
	// private String[] data;
	private static LayoutInflater inflater = null;
	
	public Customlist(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.listrow, null);

		TextView nim = (TextView) vi.findViewById(R.id.nim);
		TextView nama = (TextView) vi.findViewById(R.id.nama);
		
		HashMap<String, String> dafcab = new HashMap<String, String>();
		dafcab = data.get(position);
		
		nim.setText(dafcab.get(TAG_SM_ID));
		nama.setText(dafcab.get(TAG_PERIHAL));

		return vi;
	}
}