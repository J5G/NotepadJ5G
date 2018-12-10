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

    public int updateNote(Controller note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Controller.columnText, note.getNote());
        return db.update(Controller.tableName,values,Controller.columnId + " =? ", new String[]{
                String.valueOf(note.getNotesID())});
    }

    private Bitmap getBitmapFromEncodedString(String encodedString){

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

    public Cursor viewSortTitleData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DATE, TITLE FROM NOTES ORDER BY TITLE";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewSortDateData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DATE, TITLE FROM NOTES ORDER BY DATE";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getNotesLocation()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT TITLE, COORDINATES, TEXT, DATE FROM NOTES";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }

}
