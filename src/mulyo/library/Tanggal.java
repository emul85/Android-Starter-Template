package mulyo.library;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tanggal {
	public Tanggal() {
		
	}
	@SuppressLint("SimpleDateFormat")
	public String cariTanggalSekarang(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return (dateFormat.format(date));
	}
}
