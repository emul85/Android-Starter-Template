package mulyo.model;

public class BeritaModel {
	public String judul;
	public String tgl;
	public String gambar;
	public String link;
	public String isi;
	public BeritaModel(String judul, String tgl, String gambar, String link, String isi) {
	    this.judul = judul;
	    this.tgl = tgl;
	    this.gambar = gambar;
	    this.link = link;
	    this.isi = isi;
	}
	public String getJudul(){
		return this.judul;
	}
	public String getTgl(){
		return this.tgl;
	}
	public String getGambar(){
		return this.gambar;
	}
	public String getLink(){
		return this.link;
	}
	public String getIsi(){
		return this.isi;
	}
}
