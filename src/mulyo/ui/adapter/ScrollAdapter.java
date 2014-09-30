package mulyo.ui.adapter;

import java.util.List;
import com.mulyo.framework.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import mulyo.model.BeritaModel;;
public class ScrollAdapter extends BaseAdapter {
    private Context mContext;
    private List<BeritaModel> semuaBerita;
    public ScrollAdapter(Context context, List<BeritaModel> semuaBerita) {
        mContext = context;
        this.semuaBerita = semuaBerita;
    }
    @SuppressLint("InflateParams")
	@Override
    public View getView(int posisi, View view, ViewGroup viewGroup) {
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 		View list_berita;
 		if (view == null) {
 			list_berita = new View(mContext);
 			list_berita = inflater.inflate(R.layout.berita_item, null);
		} else {
			list_berita = (View) view;
		}
 		// disni mulai diset untuk teknya
 		TextView tanggal = (TextView) list_berita.findViewById(R.id.tek_tanggal);
 		tanggal.setText(semuaBerita.get(posisi).getTgl());
 		TextView tek_judul = (TextView) list_berita.findViewById(R.id.tek_judul);
 		tek_judul.setText(semuaBerita.get(posisi).getJudul());
		return list_berita;
    }
    public int getCount() {
    	return semuaBerita.size();
    }
    public void addBerita(List<BeritaModel> semuaBerita) {
    	// disini datanya ditambahkan ke list dan akan berubah listnya begitu
        semuaBerita = semuaBerita;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int arg0) {
        return arg0;
    }
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
    
    
}