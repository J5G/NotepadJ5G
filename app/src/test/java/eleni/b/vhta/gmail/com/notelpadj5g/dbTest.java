package eleni.b.vhta.gmail.com.notelpadj5g;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class dbTest {
    private Context context;

    @Test
    public void testDropDB()
    {
        assertTrue(context.deleteDatabase(Database.databaseName));
    }

    @Test
    public void testCreateDB(){
        Database dbhelper = new Database(context);
        SQLiteDatabase db= dbhelper.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }
}
