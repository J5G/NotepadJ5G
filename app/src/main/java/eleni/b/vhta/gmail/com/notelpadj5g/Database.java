package eleni.b.vhta.gmail.com.notelpadj5g;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.ViewDebug;

import java.sql.Blob;

public class Database extends SQLiteOpenHelper
{

    private static final int databaseVersion = 4;
    private static final String databaseName = "Notepad";
    int noteid;


    public Database (Context context)
    {
        super(context, databaseName, null, databaseVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase db)

    {
        db.execSQL(Controller.createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Controller.tableName);
        onCreate(db);
    }

    public long insertNote(String title, String text,String date, String coordinates, int bold, int italics, int underline, Blob record, String photograph){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Controller.columnTitle, title);
        values.put(Controller.columnText, text);
        values.put(Controller.columnDate,date);
        values.put(Controller.columnCoordinates, coordinates);
        values.put(Controller.columnBold, bold);
        values.put(Controller.columnItalics, italics);
        values.put(Controller.columnUnderline, underline);
        values.put(Controller.columnPhotograph, photograph);

        // the db.insert(.... returns the row ID of the newly inserted row
        // or -1 if an error occurred

        long notesID = db.insert(Controller.tableName,null,values);
        // testing:
        System.out.println(notesID);
        db.close();
        return notesID;
    }

    public boolean updateNote(String title, String text,String date, String coordinates, int bold, int italics, int underline, String record, String photograph){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Controller.columnTitle, title);
        values.put(Controller.columnText, text);
        values.put(Controller.columnDate,date);
        values.put(Controller.columnCoordinates, coordinates);
        values.put(Controller.columnBold, bold);
        values.put(Controller.columnItalics, italics);
        values.put(Controller.columnUnderline, underline);
        values.put(Controller.columnPhotograph, photograph);
        db.update(Controller.tableName,values,"ID = ?",new String[]{String.valueOf(noteid)});
        return true;
    }

    public Bitmap getBitmapFromEncodedString(String encodedString){

        byte[] arrimg = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arrimg, 0, arrimg.length);
        return img;

    }

    public void delete(String item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int position = item.indexOf("-");
        String date = item.substring(0,position-1);
        String title = item.substring(position+1 ,item.length());
        String query = "DELETE FROM NOTES WHERE TITLE LIKE '%"+title+"%' OR DATE LIKE '%"+date+"%'";
        db.execSQL(query);

    }
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DATE, TITLE FROM NOTES";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }

    public int getNoteID(String item)
    {
        int id;
        SQLiteDatabase db = this.getReadableDatabase();
        int position = item.indexOf("-");
        String date = item.substring(0,position-1);
        String title = item.substring(position+1 ,item.length());
        String query = "SELECT ID FROM NOTES WHERE TITLE LIKE '%"+title+"%' OR DATE LIKE '%"+date+"%'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        id = cursor.getInt(0);
        noteid = id;
        return id;
    }
    String ntitle;
    String nnote;
    String ndate;
    String ncoordinates;
    int nbold;
    int nitalics;
    int nunderline;
    String nphoto;
    String nrecord;
    public void setNoteByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT TEXT FROM NOTES WHERE ID = " + id;
        String note = db.rawQuery(query, null).toString();
        nnote = note;

    }

    public void setTitleByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT TITLE FROM NOTES WHERE ID = "+ id;
        String title = db.rawQuery(query, null).toString();
        ntitle = title;
    }

    public void setDateByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DATE FROM NOTES WHERE ID = "+ id;
        String date = db.rawQuery(query, null).toString();
        ndate = date;
    }

    public void setCoordinatesByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COORDINATES FROM NOTES WHERE ID = "+ id;
        String coordinates = db.rawQuery(query, null).toString();
        ncoordinates = coordinates;
    }

    public void setBoldByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT BOLD FROM NOTES WHERE ID = "+ id;
        String sbold = db.rawQuery(query, null).toString();
        int bold = Integer.parseInt(sbold);
        nbold = bold;
    }

    public void setItalicsByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ITALICS FROM NOTES WHERE ID = "+ id;
        String sitalics = db.rawQuery(query, null).toString();
        int italics = Integer.parseInt(sitalics);
        nitalics = italics;
    }

    public void setUnderlinecsByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT UNDERLINE FROM NOTES WHERE ID = "+ id;
        String sunderline = db.rawQuery(query, null).toString();
        int underline = Integer.parseInt(sunderline);
        nunderline = nunderline;
    }

    public void setPhotoByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  PHOTOGRAPH FROM NOTES WHERE ID = "+ id;
        String photo = db.rawQuery(query, null).toString();
        nphoto = nphoto;
    }

    public void setRecordByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  RECORD FROM NOTES WHERE ID = "+ id;
        String record = db.rawQuery(query, null).toString();
        nrecord = record;
    }

    public String getNtitle() {
        return ntitle;
    }

    public String getNnote() {
        return nnote;
    }

    public String getNdate() {
        return ndate;
    }

    public String getNcoordinates() {
        return ncoordinates;
    }

    public int getNbold() {
        return nbold;
    }

    public int getNitalics() {
        return nitalics;
    }

    public int getNunderline() {
        return nunderline;
    }

    public String getNphoto() {
        return nphoto;
    }

    public String getNrecord() {
        return nrecord;
    }

}
