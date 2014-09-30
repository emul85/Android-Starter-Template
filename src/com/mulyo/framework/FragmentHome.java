package com.mulyo.framework;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHome extends Fragment {
	TextView tek;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		int position = getArguments().getInt("posisi_menu");
		String[] countries = getResources().getStringArray(R.array.judul_menu);
        tek = (TextView)rootView.findViewById(R.id.txtLabel);
        tek.setText(countries[position]);
        return rootView;
    }
}
