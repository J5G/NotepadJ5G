package eleni.b.vhta.gmail.com.notelpadj5g;

import org.junit.Test;
import static org.junit.Assert.*;

public class Map_Test2 {
    @Test
    public void checkLocationReturned()
    {
       Map mMap = new Map();
       Controller con = new Controller();
       String nullLocationTest = null;
       mMap.getDeviceLocation();
       String location =con.getCoordinates();
       assertNotEquals(nullLocationTest,location);
    }
}
