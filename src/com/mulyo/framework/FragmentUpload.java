package com.mulyo.framework;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentUpload extends Fragment implements OnClickListener {
	private Button tbl_pilih, tbl_upload;
	private TextView pesan;
	private ImageView gambarView;
	private int serverResponse = 0;
	private ProgressDialog progres = null;
	protected Context kontek;
	private String server="http://appmulyo.0fees.net/upload/upload.php", lokasiGambar="";
	public FragmentUpload(Context kontek) {
		this.kontek = kontek;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_upload, container, false);
		tbl_pilih = (Button) v.findViewById(R.id.tbl_pilih);
		tbl_upload = (Button) v.findViewById(R.id.tbl_upload);
		pesan = (TextView) v.findViewById(R.id.pesan);
		gambarView = (ImageView) v.findViewById(R.id.gambarView);
		
		tbl_pilih.setOnClickListener(this);
		tbl_upload.setOnClickListener(this);
		return v;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tbl_pilih:
			Intent akt = new Intent();
			akt.setType("image/*");
			akt.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(akt, "Pilih aplikasi"), 1);
			break;
		case R.id.tbl_upload:
			progres = ProgressDialog.show(kontek, "Uploading", "Proses uploading");
			new Thread(new Runnable() {
				@Override
				public void run() {
					uploadFile(lokasiGambar);
				}
			}).start();
			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && resultCode == Activity.RESULT_OK){
			Uri selectedImageUri = data.getData();
			lokasiGambar = getLokasiGambar(selectedImageUri);
			Bitmap bitmap = BitmapFactory.decodeFile(lokasiGambar);
			gambarView.setImageBitmap(bitmap);
			pesan.setText("Folder gambar = " + lokasiGambar);
		}
	}
	private String getLokasiGambar(Uri uri){
		String hasil="";
		String[] proyeksi = {MediaStore.Images.Media.DATA};
		Cursor c = kontek.getContentResolver().query(uri, proyeksi, null, null, null);
		if(c.moveToFirst()){
			int col = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			hasil = c.getString(col);
		}
		c.close();
		return hasil;
	}
	private int uploadFile(String lokasi){
		String fileName = lokasi;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;  
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(lokasi); 
		if (!sourceFile.isFile()) {
		    progres.dismiss(); 
		    Log.d("uploadFile", "Source File not exist :" + lokasiGambar);
		    getActivity().runOnUiThread(new Runnable() {
	        public void run() {
	            pesan.setText("Source File not exist :" + lokasiGambar);
	            }
	        }); 
	        return 0;
		}
		else{
			try {
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(server);
                
                conn = (HttpURLConnection) url.openConnection(); 
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("gambarUpload", fileName);  // gambarUpload harus sesuai <input name="gambarUpload" type="file"
                
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd); 
                dos.writeBytes("Content-Disposition: form-data; name=\"gambarUpload\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                bytesAvailable = fileInputStream.available(); 
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
	            buffer = new byte[bufferSize];
	            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	            while (bytesRead > 0) {
	                 dos.write(buffer, 0, bufferSize);
	                 bytesAvailable = fileInputStream.available();
	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
	            dos.writeBytes(lineEnd);
	            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	            serverResponse = conn.getResponseCode();
	            if(serverResponse == 200){
	            	getActivity().runOnUiThread(new Runnable() {
						public void run() {
							pesan.setText("file berhasil diupload");
						}
					});
	            }
	            fileInputStream.close();
				dos.flush();
				dos.close();
			}catch (MalformedURLException ex) {
	            progres.dismiss();  
	            ex.printStackTrace();
	            getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	              	  	pesan.setText("MalformedURLException Exception : check script url.");
	                }
	            });
			}
			catch (final Exception e) {
				progres.dismiss();  
	            e.printStackTrace();
	            getActivity().runOnUiThread(new Runnable() {
					public void run() {
						pesan.setText("ada error " + e.getMessage());
					}
				});
			} // end catch
			progres.dismiss();
			return serverResponse;
		} // end else
	}
}

