package eleni.b.vhta.gmail.com.notelpadj5g;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Date_test {
    @Test
    public void checkDate() {

        Editor editor = new Editor();
        Controller cntlr = new Controller();
        String nullDateTest = null;
        editor.ReturnDate();
        String date = cntlr.getDate();
        if (date == null) {
            assertEquals(nullDateTest, date);
        } else {
            assertNotEquals(nullDateTest,date);
        }
    }
}
