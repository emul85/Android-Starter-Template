package com.mulyo.framework;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import mulyo.library.JSONParser;
import mulyo.ui.adapter.InfiniteScrollAdapter;
import mulyo.ui.adapter.ScrollAdapter;

public class ActivityScroll_Lama extends Activity{
	/*
	private InfiniteScrollAdapter<ScrollAdapter> mAdapter;
	private Handler mHandler;
	private SwipeRefreshLayout swipe;
	private ProgressBar spinner;
	private ListView listberita;
	private ArrayList<String> judul;
	private ScrollAdapter adp_item;
	private Context kontek;
	private LinearLayout progress;
	private JSONParser jsonParser = new JSONParser();
	private String serverUrl = "http://appmulyo.0fees.net/json/index.php";
	int i=0;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scroll);
		kontek = this;
		spinner = (ProgressBar) findViewById(R.id.progress_spinner);
		progress = new LinearLayout(this);
        progress.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 100));
        progress.setGravity(Gravity.CENTER);
        progress.addView(new ProgressBar(this));
        
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipe.setOnRefreshListener(this);
		swipe.setColorScheme(android.R.color.holo_blue_bright, 
				android.R.color.holo_green_light, 
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);
        

        listberita = ((ListView) findViewById(R.id.listView));
        LoadingAjak load = new LoadingAjak();
		load.execute();
	}
	private ArrayList<String> appendData(){
		if(judul == null) judul =  new ArrayList<String>();
		for (int a=0; a<13; a++) {
        	i++;
        	judul.add("List " + i);
        }
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("awal", "0"));
		params.add(new BasicNameValuePair("akhir", "10"));
		JSONObject json = jsonParser.getJSONFromUrl(serverUrl, params);
		Log.d("json", json.toString());
		
        return judul;
	}
	private class LoadingAjak extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			if(! spinner.isShown()) spinner.setVisibility(View.VISIBLE);
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean hasil = false;
			appendData();
			hasil = true;
			return hasil;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if( spinner.isShown()) spinner.setVisibility(View.GONE);
			if(result){
				adp_item = new ScrollAdapter(kontek, judul);
		        mAdapter = new InfiniteScrollAdapter<ScrollAdapter>(kontek, adp_item, progress);
		        mAdapter.addListener(new InfiniteScrollAdapter.InfiniteScrollListener() {
					@Override
					public void onInfiniteScrolled() {
						mHandler.postDelayed(new Runnable() {
				            @Override
				            public void run() {
				            	appendData();
				            	Log.d("resfresh bawah", "dieksekusi, panjang" + judul.size());
				                mAdapter.getAdapter().addBerita(judul);
				                mAdapter.handledRefresh();
				            }
				        }, 3000);
					}
				});
		        mHandler = new Handler();
				listberita.setAdapter(mAdapter);
			}
			super.onPostExecute(result);
		}
	}
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	appendData();
            	Log.d("resfresh atas", "dieksekusi, panjang" + judul.size());
                mAdapter.getAdapter().addBerita(judul);;
                mAdapter.handledRefresh();
                swipe.setRefreshing(false);
            }
        }, 3000);		
	}
	*/
}
