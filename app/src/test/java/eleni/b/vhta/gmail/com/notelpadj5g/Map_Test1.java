package eleni.b.vhta.gmail.com.notelpadj5g;

import org.junit.Test;
import static org.junit.Assert.*;

public class Map_Test1 {
    @Test
    public void checkIfPermissionsGranted()
    {
        Boolean mLocationPermissionsGranted = false;
        Map mMap = new Map();
        mMap.getLocationPermission();
        assertTrue(mLocationPermissionsGranted);
    }
}
