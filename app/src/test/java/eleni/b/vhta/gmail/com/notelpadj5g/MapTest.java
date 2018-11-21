package eleni.b.vhta.gmail.com.notelpadj5g;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest
{
    @Test
    public void getDeviceLocationLocationFoundCorrectly ()
    {
        Map map = new Map();
        Controller cntrlr = new Controller();
        String expected = "41.0881099 23.5521829";
        map.getDeviceLocation();
        String result = cntrlr.getCoordinates();
        assertEquals(expected, result);
    }
    @Test
    public void getDeviceLocationException()
    {
        Map map = new Map();
        try
        {
            map.getDeviceLocation();
            fail();
        }catch(SecurityException e)
        {
            assertTrue(true);
        }
    }
}