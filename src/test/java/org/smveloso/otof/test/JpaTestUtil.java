package org.smveloso.otof.test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.smveloso.otof.em.JpaManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

/**
 *
 * @author sergio
 */
public abstract class JpaTestUtil {

    protected String dataSetLocation;     
    protected List<DatabaseOperation> beforeTestOperations = new ArrayList<>();
    protected List<DatabaseOperation> afterTestOperations = new ArrayList<>();
    private ReplacementDataSet dataSet;

    @BeforeTest(groups = "jpa-test")
    void startEMFactory() throws Exception {
        System.out.println(">>> startEMFactory");
        JpaManager.getInstance();
    }

    @AfterTest(groups = "jpa-test")
    void stopEMFactory() throws Exception {
        System.out.println(">>> stopEMFactory");
        JpaManager.getInstance().finish();
    }
    
    @BeforeClass(groups = "jpa-test")
    void prepareDataSet() throws Exception {        
        System.out.println(">>> prepareDataSet");
        System.out.println(">>> WARNING: returning only true.");
        
        if (true) {
            return;
        }

        // Check if subclass has prepared everything        
        prepareSettings();
        if (dataSetLocation == null) {
            throw new RuntimeException("Test subclass needs to prepare a dataset location");        
        }
        // Load the base dataset file        
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(dataSetLocation); 
        dataSet = new ReplacementDataSet(buildFlatXmlDataSet(input));        
        dataSet.addReplacementObject("[NULL]", null);    
    }    
    
    private FlatXmlDataSet buildFlatXmlDataSet(InputStream input) throws DataSetException {
        return new FlatXmlDataSetBuilder().build(input);
    }
    
    @BeforeMethod(groups = "jpa-test")     
    void beforeTestMethod() throws Exception {        
        System.out.println(">>> beforeTestMethod");
        for (DatabaseOperation op : beforeTestOperations ) {
            op.execute(getConnection(), dataSet);        
        }    
    }      
    
    @AfterMethod(groups = "jpa-test")     
    void afterTestMethod() throws Exception {
        System.out.println(">>> afterTestMethod");
        for (DatabaseOperation op : afterTestOperations ) {            
            op.execute(getConnection(), dataSet);        
        }    
    }    

    // Subclasses can/have to override the following methods    
    protected IDatabaseConnection getConnection() throws Exception {         
        
        // Get a JDBC connection from Hibernate        
        // WARNING: https://stackoverflow.com/questions/3493495/getting-database-connection-in-pure-jpa-setup
        Connection con = ((Session) JpaManager.getInstance().getEntityManager().unwrap(Session.class)).connection();
        // Disable foreign key constraint checking
        con.prepareStatement("set referential_integrity FALSE").execute();        
        return new DatabaseConnection(con);
    }    
    
    protected abstract void prepareSettings(); 
        
}