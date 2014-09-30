package com.mulyo.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class DashboardActivity extends ActionBarActivity {
	int posisi_menu = -1;	
	String judul = "";
	String[] semua_judul_menu  = new String[]{"Home", "About", "Refresh", "Photo", "Upload", "Scroll", "Logout"};
	int[] icon_menu = new int[]{
        R.drawable.ic_home,
        R.drawable.ic_launcher,
        android.R.drawable.ic_menu_rotate,
        android.R.drawable.ic_menu_gallery,
        android.R.drawable.ic_input_add,
        android.R.drawable.ic_dialog_alert,
        android.R.drawable.ic_lock_lock
	};
	String[] notifikasi_menu = new String[]{"", "", "", "", "", "", "", ""};
	private DrawerLayout layout_menu;	
	private ListView list_menu;	
	private ActionBarDrawerToggle tombol_togle;	
	private LinearLayout tempat_menu;	
	private List<HashMap<String,String>> item_menu;	
	private SimpleAdapter adapter_list;	
	final private String JUDUL = "judul";	
	final private String ICON = "icon";	
	final private String NOTIFIKASI = "notifikasi";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);
		//semua_judul_menu = getResources().getStringArray(R.array.judul_menu);
		judul = (String)getTitle();
		list_menu = (ListView) findViewById(R.id.drawer_list);
		tempat_menu = ( LinearLayout) findViewById(R.id.drawer);
		item_menu = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<semua_judul_menu.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(JUDUL, semua_judul_menu[i]);
            hm.put(NOTIFIKASI, notifikasi_menu[i]);
            hm.put(ICON, Integer.toString(icon_menu[i]) );
            item_menu.add(hm);
        }
		String[] from = { ICON,JUDUL,NOTIFIKASI };
		int[] to = { R.id.icon , R.id.judul , R.id.notifikasi};
		adapter_list = new SimpleAdapter(this, item_menu, R.layout.drawer_layout, from, to);
		layout_menu = (DrawerLayout)findViewById(R.id.drawer_layout); 
		tombol_togle = new ActionBarDrawerToggle(this, layout_menu, R.drawable.ic_drawer , R.string.drawer_open,R.string.drawer_close){
			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {               
				gantiJudulAtas();            		
				supportInvalidateOptionsMenu();       
			}
			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {            	
				getSupportActionBar().setTitle("Select a Menu");            	
				supportInvalidateOptionsMenu();                
			}
		};
		layout_menu.setDrawerListener(tombol_togle);
		list_menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				//incrementHitCount(position);		
				changeFragment(position);
				layout_menu.closeDrawer(tempat_menu);		
			}
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); 
        list_menu.setAdapter(adapter_list); 
        
        if(savedInstanceState == null){
        	changeFragment(0);
        }
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		tombol_togle.syncState();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (tombol_togle.onOptionsItemSelected(item)) {
			return true;
	    }
		return super.onOptionsItemSelected(item);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void incrementHitCount(int position){
		HashMap<String, String> item = item_menu.get(position);
		String count = item.get(NOTIFIKASI);
		item.remove(NOTIFIKASI);
		if(count.equals("")){
			count = "  1  ";
		}else{
			int cnt = Integer.parseInt(count.trim());
			cnt ++;
			count = "  " + cnt + "  ";
		}				
		item.put(NOTIFIKASI, count);				
		adapter_list.notifyDataSetChanged();
	}
	public void changeFragment(int posisi){
		judul = semua_judul_menu[posisi];	
		Fragment fragmen = null;
		Bundle data = null;
		switch (posisi) {
		case 0:
			fragmen = new FragmentHome();
			data = new Bundle();
	        data.putInt("posisi_menu", posisi);
	        fragmen.setArguments(data);
			break;
		case 1:
			fragmen = new FragmentAbout();
			data = new Bundle();
			data.putInt("posisi_menu", posisi);
			break;
		case 2:
			//fragmen = new FragmentBerita(DashboardActivity.this);
			break;
		case 3:
			fragmen = new FragmentPhoto();
			break;
		case 4:
			fragmen = new FragmentUpload(DashboardActivity.this);
			break;
		case 5:
			Intent i = new Intent(DashboardActivity.this, ActivityScroll.class);
			startActivity(i);
			break;
		default:
			break;
		}
		
		// jika tidak sama dengan null maka diganti
		if(fragmen != null){
			FragmentManager fragmentManager  = getSupportFragmentManager();
	        FragmentTransaction ft = fragmentManager.beginTransaction();
	        ft.replace(R.id.content_frame, fragmen); // R.id.content_frame mrpk id dari layout fragment nya
	        ft.commit();
		}
	}
	public void gantiJudulAtas(){
		int selectedItem = list_menu.getCheckedItemPosition();
    	posisi_menu = selectedItem;
    	if(posisi_menu != -1) getSupportActionBar().setTitle(semua_judul_menu[posisi_menu]);
	}	
}
