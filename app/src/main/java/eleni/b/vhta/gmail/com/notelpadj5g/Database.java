package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    private static final int databaseVersion = 1;
    private static final String databaseName = "NOTEPAD";


    public Database (Context context)
    {
        super(context, databaseName, null, databaseVersion);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Controller.createNotesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Controller.tableName1);
        onCreate(db);
    }

}
