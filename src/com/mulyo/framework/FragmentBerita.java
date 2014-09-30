package com.mulyo.framework;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import mulyo.library.JSONParser;
import mulyo.ui.adapter.InfiniteScrollAdapter;
import mulyo.ui.adapter.ScrollAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;

public class FragmentBerita extends Fragment /*implements OnRefreshListener*/ {
	/*
	private Context kontek;
	private InfiniteScrollAdapter<ScrollAdapter> mAdapter;
	//private Handler mHandler;
	private SwipeRefreshLayout swipe;
	private ProgressBar spinner;
	private ListView listberita;
	private ArrayList<String> judul;
	private ScrollAdapter adp_item;
	private LinearLayout progress;
	private JSONParser jsonParser = new JSONParser();
	private String serverUrl = "http://appmulyo.0fees.net/json/index.php";
	private LoadingAjak loading;
	int i=0;
	public FragmentBerita(Activity kon){
		kontek = kon;
	}
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_berita, container, false);
		
		spinner = (ProgressBar) v.findViewById(R.id.progress_spinner);
		progress = new LinearLayout(kontek);
		judul = new ArrayList<String>();
		
        progress.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 100));
        progress.setGravity(Gravity.CENTER);
        progress.addView(new ProgressBar(kontek));
        
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
		swipe.setOnRefreshListener(this);
		swipe.setColorScheme(android.R.color.holo_blue_bright, 
				android.R.color.holo_green_light, 
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);
        
		
        listberita = ((ListView) v.findViewById(R.id.listView));
        
		if(! spinner.isShown()) spinner.setVisibility(View.VISIBLE);
        loading = new LoadingAjak();
        loading.execute();
        
		return v;
	}
	private class LoadingAjak extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean hasil = false;
			hasil = appendData();
			return hasil;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if( spinner.isShown()) spinner.setVisibility(View.GONE);
			if(result){
			 	adp_item = new ScrollAdapter(kontek, judul);
			 	if(mAdapter == null){
			 		mAdapter = new InfiniteScrollAdapter<ScrollAdapter>(kontek, adp_item, progress);
			 		mAdapter.addListener(new InfiniteScrollAdapter.InfiniteScrollListener() {
						@Override
						public void onInfiniteScrolled() {
							loading = new LoadingAjak();
							loading.execute();
						}
					});
			 		listberita.setAdapter(mAdapter);
			 		Log.d("resfresh awal", "dieksekusi, panjang" + judul.size());
			 	}
			 	else{
	            	Log.d("resfresh bawah", "dieksekusi, panjang" + judul.size());
	                mAdapter.getAdapter().addBerita(judul);
	                mAdapter.handledRefresh();
			 	}
			}
			super.onPostExecute(result);
		}
	}
	@Override
	public void onRefresh() {
		loading = new LoadingAjak();
		loading.execute();
		swipe.setRefreshing(false);
		Log.d("swipe atas", "dieksekusi");
	}
	private boolean appendData(){
		if(judul == null) judul =  new ArrayList<String>();
		for (int a=0; a<13; a++) {
        	i++;
        	judul.add("List " + i);
        }
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("awal", "0"));
		params.add(new BasicNameValuePair("akhir", "10"));
		JSONObject json = jsonParser.getJSONFromUrl(serverUrl, params);
		Log.d("jsonku", json.toString());
		
        return true;
	}
	*/
}
