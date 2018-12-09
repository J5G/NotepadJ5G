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
    //version of database
    private static final int databaseVersion = 5;
    //database name
    private static final String databaseName = "Notepad";

    private Context ctx;
    //column names
    public static final String tableName = "NOTES";
    public static final String columnId = "ID";
    public static final String columnText = "TEXT";
    public static final String columnTitle = "TITLE";
    public static final String columnDate = "DATE";
    public static final String columnCoordinates = "COORDINATES";
    public static final String columnBold = "BOLD";
    public static final String columnItalics = "ITALICS";
    public static final String columnUnderline = "UNDERLINE";
    public static final String columnPhotograph = "PHOTOGRAPH";
    public static final String columnRecord = "RECORD";

    //sql query to creating table in database
    public static final String createTable="CREATE TABLE NOTES ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, TEXT TEXT, DATE TEXT, COORDINATES TEXT, BOLD INT, ITALICS INT, UNDERLINE INT, RECORD BLOB, PHOTOGRAPH TEXT )";


    public Database (Context context)
    {
        super(context, databaseName, null, databaseVersion);
        this.ctx=context;

    }

    //creating the table in database
    @Override
    public void onCreate(SQLiteDatabase db)

    {

        db.execSQL(createTable);
    }

    //in case of upgrade we are dropping the old table and we create the new one
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);

        onCreate(db);
    }

    //function for adding the note to database
    public long insertNote(String title, String text,String date, String coordinates, int bold, int italics, int underline, Blob record, String photograph){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(columnTitle, title);
        values.put(columnText, text);
        values.put(columnDate,date);
        values.put(columnCoordinates, coordinates);
        values.put(columnBold, bold);
        values.put(columnItalics, italics);
        values.put(columnUnderline, underline);
        values.put(columnPhotograph, photograph);

        // the db.insert(.... returns the row ID of the newly inserted row
        // or -1 if an error occurred

        long notesID = db.insert(tableName,null,values);
        // testing:
        System.out.println(notesID);
        //closing the database connection
        db.close();
        return notesID;
    }

    public Cursor getNotes(SQLiteDatabase db){
        Cursor c= db.query(tableName,new String[]{columnTitle,columnText},null,null,null,null, "ID DESC");
        //moving to the first note
        c.moveToFirst();
        //and returning Cursor object
        return c;
    }

    public Cursor getNotes2(SQLiteDatabase db){
        Cursor c= db.query(tableName,new String[]{columnId,columnTitle},null,null,null,null, "ID DESC");
        //moving to the first note
        c.moveToFirst();
        //and returning Cursor object
        return c;
    }

    public Cursor getNote(SQLiteDatabase db, int id){
        Cursor c= db.query(tableName,new String[]{columnTitle,columnText,columnDate}, columnId + "=?",new String[]{String.valueOf(id)},null,null,null);
        c.moveToFirst();
        return c;
    }

    public void removeNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName,columnId + "=?",new String[]{String.valueOf(id)});
        db.close();
    }


    public void updateNote(String title, String text,String date, String coordinates, int bold, int italics, int underline, Blob record, String photograph,String editTitle){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnTitle, title);
        values.put(columnText, text);
        values.put(columnDate,date);
        values.put(columnCoordinates, coordinates);
        values.put(columnBold, bold);
        values.put(columnItalics, italics);
        values.put(columnUnderline, underline);
        values.put(columnPhotograph, photograph);
        db.update(tableName,values,columnTitle + "LIKE '" + editTitle + "'",null);
        db.close();
    }

    private Bitmap getBitmapFromEncodedString(String encodedString){

        byte[] arrimg = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arrimg, 0, arrimg.length);
        return img;

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

}
