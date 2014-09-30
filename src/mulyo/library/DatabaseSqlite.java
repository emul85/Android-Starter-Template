package mulyo.library;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSqlite extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "session_db";
	// kolom tabel login
	private static final String TABLE_LOGIN = "tbl_login", KEY_ID = "id", KEY_NAME = "nama", KEY_EMAIL = "email", KEY_CREATED_AT = "created_at";
	//kolom tabel list berita
	private static final String TABLE_BERITA = "tbl_berita", JUDUL_BERITA = "judul", ID_BERITA = "id", DECP_BERITA = "deskripsi";
	public DatabaseSqlite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_ABLE_LOGIN);
		String CREATE_TABLE_BERITA = "CREATE TABLE " + TABLE_BERITA + "("
				+ ID_BERITA + " INTEGER PRIMARY KEY," 
				+ JUDUL_BERITA + " TEXT,"
				+ DECP_BERITA + " TEXT" + ")";
		db.execSQL(CREATE_TABLE_BERITA);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		onCreate(db);
	}
	public void addUser(String nama, String email, String id, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, nama); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_CREATED_AT, created_at); // Created At		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}
	public HashMap<String, String> getUserDetails(){
		HashMap<String,String> user = new HashMap<String,String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
		 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	user.put("nama", cursor.getString(1));
        	user.put("email", cursor.getString(2));
        	user.put("id", cursor.getString(3));
        	user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
		return user;
	}
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}
	public void resetTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}
	public void addBerita(String judul, String desc) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(JUDUL_BERITA, judul); 
		values.put(DECP_BERITA, desc);
		db.insert(TABLE_BERITA, null, values);
		db.close(); 
	}
	public Cursor getBerita(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor  cursor = db.rawQuery("SELECT * FROM " + TABLE_BERITA + " ORDER BY " + ID_BERITA + " ASC ",null);
		return cursor;
	}
	public void resetTabelBerita(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_BERITA, null, null);
		db.close();
	}
	
}
