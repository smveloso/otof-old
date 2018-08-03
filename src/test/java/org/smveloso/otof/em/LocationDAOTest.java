package org.smveloso.otof.em;

import org.dbunit.operation.DatabaseOperation;
import org.smveloso.otof.test.JpaBaseTest;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author sergio
 */
public class LocationDAOTest extends JpaBaseTest {
    
    public LocationDAOTest() {
    }

    @Test(groups="jpa-test")
    public void testFindLocation() throws Exception {
        fail("testing ...");
    }
    
    @Override
    protected void prepareSettings() {
        System.out.println(">>> LocationDAOTest.prepareSettings");
        dataSetLocation = "org/smveloso/otof/em/locationDAOTestDS.xml";
        beforeTestOperations.add(DatabaseOperation.DELETE_ALL);
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
        afterTestOperations.add(DatabaseOperation.DELETE_ALL);
    }
    
    
    
    
}
