package com.mulyo.framework;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import mulyo.model.BeritaModel;
import mulyo.ui.adapter.InfiniteScrollAdapter;
import mulyo.ui.adapter.ScrollAdapter;

public class ActivityScroll extends Activity implements OnRefreshListener{
	private InfiniteScrollAdapter<ScrollAdapter> mAdapter;
	private SwipeRefreshLayout swipe;
	private ProgressBar spinner;
	private ListView listberita;
	private List<BeritaModel> semuaBerita;
	private ScrollAdapter adp_item;
	private Context kontek;
	private LinearLayout progress;
	private JSONParser jsonParser = new JSONParser();
	private String serverUrl = "http://appmulyo.0fees.net/json/yql.php";
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
	private List<BeritaModel> appendData(){
		if(semuaBerita == null) semuaBerita = new ArrayList<BeritaModel>();
		try{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			/*
			params.add(new BasicNameValuePair("awal", "0"));
			params.add(new BasicNameValuePair("akhir", "10"));
			params.add(new BasicNameValuePair("format", "json"));
			*/
			String serverTemp = serverUrl + "?awal=0&akhir=10";
			Log.e("link e", serverTemp);
			JSONObject json = jsonParser.getJSONFromUrl(serverTemp, params);
			JSONObject jsonku = json.getJSONObject("query").getJSONObject("results");
			JSONArray jsonArray = jsonku.getJSONArray("item");
			for(int i=0; i<jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String judulku = jsonObject.getString("title");
                String tgl = jsonObject.getString("pubDate");
                String gambar = jsonObject.getJSONObject("content").getString("url");
                String link = jsonObject.getString("link");
                String isi = jsonObject.getJSONObject("text").getString("content");
                Log.d("isi", isi);
                semuaBerita.add(new BeritaModel(judulku, tgl, gambar, link, isi));
			}
		}catch(Exception e){}
        return semuaBerita;
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
				adp_item = new ScrollAdapter(kontek, semuaBerita);
		        mAdapter = new InfiniteScrollAdapter<ScrollAdapter>(kontek, adp_item, progress);
		        mAdapter.addListener(new InfiniteScrollAdapter.InfiniteScrollListener() {
					@Override
					public void onInfiniteScrolled() {
						mAdapter.getAdapter().addBerita(semuaBerita);;
				        mAdapter.handledRefresh();
						Log.d("resfresh bawah", "dieksekusi, panjang" + semuaBerita.size());
					}
				});
				listberita.setAdapter(mAdapter);
			}
			super.onPostExecute(result);
		}
	}
	private class LoadingLagi extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			appendData();
	    	mAdapter.getAdapter().addBerita(semuaBerita);;
	        mAdapter.handledRefresh();
			return null;
		}
		
	}
	@Override
	public void onRefresh() {
		appendData();
    	Log.d("resfresh atas", "dieksekusi, panjang" + semuaBerita.size());
        mAdapter.getAdapter().addBerita(semuaBerita);;
        mAdapter.handledRefresh();
        swipe.setRefreshing(false);
	}
}
