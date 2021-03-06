package eleni.b.vhta.gmail.com.notelpadj5g;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Blob;

public class Database extends SQLiteOpenHelper
{
    //version of database

    private static final int databaseVersion = 4;

    //database name

    public static final String databaseName = "Notepad";


    public Database (Context context)
    {
        super(context, databaseName, null, databaseVersion);

    }

    //creating the table in database

    @Override
    public void onCreate(SQLiteDatabase db)

    {
        db.execSQL(Controller.createTable);
    }

    //in case of upgrade we're dropping the old table, and create the new one

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Controller.tableName);
        onCreate(db);
    }

    //function for adding the note to database

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

    public int updateNote(int id, String text, String date, String coordinates, int bold, int italics, int underline, String photo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Controller.columnText, text);
        values.put(Controller.columnDate, date);
        values.put(Controller.columnCoordinates, coordinates);
        values.put(Controller.columnBold, bold);
        values.put(Controller.columnItalics, italics);
        values.put(Controller.columnUnderline, underline);
        values.put(Controller.columnPhotograph, photo);

        return db.update(Controller.tableName,values,Controller.columnId + " =? ", new String[]{
                String.valueOf(id)});
    }

    public void delete(int noteID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM NOTES WHERE ID = "+noteID;
        db.execSQL(query);

    }
    public Cursor viewData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DATE, TITLE FROM NOTES";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewSortTitleData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DATE, TITLE FROM NOTES ORDER BY TITLE";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }

    public Cursor viewSortDateData()
    {
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
    public int getNoteID(String title)
    {
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ID FROM NOTES WHERE TITLE ='"+title+"'";
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst())
        {
            id=c.getInt(0);
            return id;
        }

        return id;
    }

    public Cursor getData(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM NOTES WHERE ID = "+id;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
}
