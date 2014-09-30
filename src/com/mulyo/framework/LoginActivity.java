package com.mulyo.framework;

import mulyo.library.SessionUser;
import mulyo.library.DatabaseSqlite;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	TextView link_to_register, tek_error_login;
	Button btnLogin;
	EditText t_email, t_pass;
	String email, pass;
	SessionUser user_session = new SessionUser();
	DatabaseSqlite userdb = new DatabaseSqlite(this);
	JSONObject json, json_user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		/*
		 * cek user sudah login belum
		 * */
		if(user_session.isUserLoggedIn(this)){
			Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
		link_to_register = (TextView) findViewById(R.id.link_to_register);
		t_email = (EditText) findViewById(R.id.input_email);
		t_pass = (EditText) findViewById(R.id.input_password);
		tek_error_login = (TextView) findViewById(R.id.tek_error_login);
		link_to_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, ForgotActivity.class);
				startActivity(i);
				finish();
			}
		});
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tek_error_login.setText("");
				LoadingTask loading = new LoadingTask();
				loading.execute();
			}
		});
	}
	
	private class LoadingTask extends AsyncTask<Void, Void, Boolean >{ // type boolean, string atau int ditentukan di paling akhir dari 3 param
		ProgressDialog p = new ProgressDialog(LoginActivity.this);
        @Override
        protected void onPreExecute() {
        	super.onPreExecute();
        	p.setTitle("loading");
            p.setMessage("Authenticating...");
            p.setIndeterminate(true);
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setCancelable(false);
            if(! p.isShowing()) p.show();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
        	Boolean hasil = false;
        	email = t_email.getText().toString();
			pass = t_pass.getText().toString();
			json = user_session.loginUser(email, pass);
			try {
				if(json.getBoolean("login")){
					user_session.logoutUser(getApplicationContext());
					json_user = json.getJSONObject("user");
										
					String created_at = user_session.cariTanggalSekarang();
					userdb.addUser(json_user.getString("nama"), json_user.getString("email"), json_user.getString("id"), created_at);
					hasil = true;					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
        	return hasil;
        }
        protected void onPostExecute(Boolean result) {
        	super.onPostExecute(result);
            if (p.isShowing())  p.dismiss();
            if(! result){
            	tek_error_login.setText("Login gagal");
            	Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
            }else{
            	Intent a = new Intent(LoginActivity.this, DashboardActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                finish();
            }
        }
	}

}
