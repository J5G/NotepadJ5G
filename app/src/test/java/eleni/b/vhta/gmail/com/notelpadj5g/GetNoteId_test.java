package eleni.b.vhta.gmail.com.notelpadj5g;

import org.junit.Test;
import android.content.Context;
import static org.junit.Assert.*;

public class GetNoteId_test
{
    private Context context;
    Database db = new Database(context);

    @Test
    public void getNoteIdReturnsNull()
    {
        int expected = 0;
        int result = db.getNoteID(null);

        assertEquals(expected,result);
    }
}
