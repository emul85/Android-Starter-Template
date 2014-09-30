package com.mulyo.framework;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentPhoto extends Fragment implements OnClickListener{
	private Context kontek;
	final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
	private Uri imageUri                      = null;
    private static TextView detailGambar      = null;
    private  static ImageView tempatGambar  = null;
    private Button tbl_photo = null;
    
    public FragmentPhoto() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_photo, container, false);
		detailGambar = (TextView) view.findViewById(R.id.detailGambar);
		tempatGambar = (ImageView) view.findViewById(R.id.tempatGambar);
		kontek = getActivity();
		tbl_photo = (Button) view.findViewById(R.id.tbl_photo);
		tbl_photo.setOnClickListener(this);
		
		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tbl_photo:
			ambilPhoto();
			break;

		default:
			break;
		}
	}
	private void ambilPhoto(){
		String fileName = "Camera_Example.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION,"Gambar diambil dari aplikasi mulyo");
        imageUri = kontek.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
          
        startActivityForResult( intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			getActivity();
			if(resultCode == getActivity().RESULT_OK){
				// disini load photo
				String imageId = convertImageUriToFile(imageUri, getActivity());
				new LoadImageDariSDcard().execute(imageId);
			}
			else if(resultCode == getActivity().RESULT_CANCELED){
				Toast.makeText(kontek, "Ambil photo dicancel", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(kontek, "Ambil photo gagal", Toast.LENGTH_SHORT).show();
			}
		}
	}
	public String convertImageUriToFile ( Uri imageUri, Activity activity ){
		Cursor c = null;
		int imageID = 0;
		int thumbID = 0;
		try {
			String[] proy = {
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media._ID,
				MediaStore.Images.Thumbnails._ID,
				MediaStore.Images.ImageColumns.ORIENTATION
			};
			c = kontek.getContentResolver().query(imageUri, proy, null, null, null);
			int columnImage = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			int columnThumb = c.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
			int columnFile = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int size = c.getCount(); // jika 0 berati tidak ada gambar
			if(size == 0){
				detailGambar.setText("tidak ada gambar di sd card");
			}
			else{
				if(c.moveToFirst()){
					imageID = c.getInt(columnImage);
					thumbID = c.getInt(columnThumb);
					String lokasi = c.getString(columnFile);
					detailGambar.setText("ID gambar = " + imageID + "\n" +
						"ID thumbnail = " + thumbID + "\n" +
						"lokasi = " + lokasi + "\n"
					);
				}
				c.close();
			}
			
		} catch (Exception e) {
		}
		
		return "" + imageID;
	}
	public class LoadImageDariSDcard extends AsyncTask<String, Void, Void>{
		private ProgressDialog progres = new ProgressDialog(kontek);
		Bitmap bitmap;
		@Override
		protected void onPreExecute() {
			progres.setMessage("Gambar diload");
			progres.setTitle("Loading");
			if(!progres.isShowing()) progres.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(String... link) {
			Bitmap gbr, gbrnew;
			Uri uri;
			try {
				uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, link[0]);
				gbr = BitmapFactory.decodeStream(kontek.getContentResolver().openInputStream(uri));
				if(gbr != null){
					gbrnew = Bitmap.createScaledBitmap(gbr, 150, 150, true);
					gbr.recycle();
					if(gbrnew != null) bitmap = gbrnew;
				}
			} catch (IOException e) {
				if(progres.isShowing()) progres.dismiss();
				cancel(true);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			if(progres.isShowing()) progres.dismiss();
			if(bitmap != null) tempatGambar.setImageBitmap(bitmap);
			super.onPostExecute(result);
		}
	}
}
