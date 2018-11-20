package eleni.b.vhta.gmail.com.notelpadj5g;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper
{

    private static final int databaseVersion = 2;
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

    public long insertNote(String title, String text, String coordinates, int bold, int italics, int underline, Blob record, Blob phoograph){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Controller.columnTitle, title);
        values.put(Controller.columnText, text);
        values.put(Controller.columnCoordinates, coordinates);
        values.put(Controller.columnBold, bold);
        values.put(Controller.columnItalics, italics);
        values.put(Controller.columnUnderline, underline);

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

    public Cursor getNotes(SQLiteDatabase db)
    {
        String[] projections = {Controller.columnId, Controller.columnTitle, Controller.columnText, Controller.columnDate, Controller.columnCoordinates, Controller.columnBold,Controller.columnItalics, Controller.columnUnderline};
        Cursor cursor = db.query(Controller.tableName,projections,null,null,null,null,null);

        return cursor;
    }

    public void deleteNote(Controller note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Controller.tableName, Controller.columnId + " = ?",
                new String[]{String.valueOf(note.getNotesID())});
        db.close();
    }
}

