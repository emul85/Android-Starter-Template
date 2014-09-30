package com.mulyo.framework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ForgotActivity extends Activity implements OnClickListener{
	TextView link_to_login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_layout);
		link_to_login = (TextView) findViewById(R.id.link_to_login);
		link_to_login.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.link_to_login:
			Intent i = new Intent(ForgotActivity.this, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			break;
		default:
			break;
		}
	}
}
