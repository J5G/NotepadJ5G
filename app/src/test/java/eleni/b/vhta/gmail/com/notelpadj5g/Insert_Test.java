package eleni.b.vhta.gmail.com.notelpadj5g;

import org.junit.Test;
import android.content.Context;
import static org.junit.Assert.*;

// Giorgos
public class Insert_Test
{
    private Context context;
    Database db = new Database(context);
    @Test
    public void InsertNotNull()
    {
        long result = db.insertNote("title","text","8/1 8:54 μ.μ.", "123456789",1, 1 ,1,null,null);
        assertNotEquals(result,null);
    }


}
