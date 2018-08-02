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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

/**
 *
 * @author sergio
 */
public abstract class JpaBaseTest {

    protected String dataSetLocation;     
    protected List<DatabaseOperation> beforeTestOperations = new ArrayList<>();
    protected List<DatabaseOperation> afterTestOperations = new ArrayList<>();
    private ReplacementDataSet dataSet;

    @BeforeTest(groups = "jpa-test")
    public void startEMFactory() throws Exception {
        System.out.println(">>> base.beforeTest");
        JpaManager.getInstance();
    }

    @AfterTest(groups = "jpa-test")
    public void stopEMFactory() throws Exception {
        System.out.println(">>> base.AfterTest");
        JpaManager.getInstance().finish();
    }
    
    @BeforeClass(groups = "jpa-test")
    public void prepareDataSet() throws Exception {        
        System.out.println(">>> base.BeforeClass");

        // Check if subclass has prepared everything        
        prepareSettings();
        if (dataSetLocation == null) {
            System.out.println(" WARN: no dataSetLocation defined.");
        } else {
            // Load the base dataset file
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(dataSetLocation); 
            if (null == input) {
                throw new Exception("dataset nao encontrado: " + dataSetLocation);
            }
            dataSet = new ReplacementDataSet(buildFlatXmlDataSet(input));        
            dataSet.addReplacementObject("[NULL]", null);
        }
    }    
    
    @AfterClass
    public void afterClassPlaceHolder() {
        System.out.println(">>> base.afterClassPlaceHolder");
    }
    
    private FlatXmlDataSet buildFlatXmlDataSet(InputStream input) throws DataSetException {
        return new FlatXmlDataSetBuilder().build(input);
    }
    
    @BeforeMethod()     
    public void beforeTestMethod() throws Exception {        
        System.out.println(">>> base.beforeTestMethod");
        for (DatabaseOperation op : beforeTestOperations ) {
            op.execute(getConnection(), dataSet);        
        }    
    }      
    
    @AfterMethod()     
    public void afterTestMethod() throws Exception {
        System.out.println(">>> base.afterTestMethod");
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