package org.smveloso.otof.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
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
        System.out.println(Thread.currentThread().getId() + ">>> base.beforeTestMethod");
        for (DatabaseOperation operation:beforeTestOperations) {
            processOperation(operation);
        }      
    }
    
    @AfterMethod()     
    public void afterTestMethod() throws Exception {
        System.out.println(Thread.currentThread().getId() + ">>> base.afterTestMethod");
        for (DatabaseOperation operation:afterTestOperations) {
            processOperation(operation);
        }   
    }

    protected abstract void prepareSettings(); 

    protected void processOperation(DatabaseOperation operation) {
        System.out.println(">>> processOperation op:" + operation.toString());        
        EntityManager em = JpaManager.getInstance().getEntityManager();
        em.getTransaction().begin();
        try {
            ((Session) em.unwrap(Session.class))
                    .doReturningWork(new InnerReturningWork(operation));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        System.out.println("<<< processOperation op:" + operation.toString());        
    }
    
    class InnerReturningWork implements ReturningWork<Void> {

        DatabaseOperation operation;
        
        public InnerReturningWork(DatabaseOperation operation) {
            this.operation = operation;
        }
        
        @Override
        public Void execute(Connection connection) throws SQLException {
            try {
                System.out.println(Thread.currentThread().getId() + ">>>InnerReturningWork for op:" + operation.toString());
                connection.prepareStatement("set referential_integrity FALSE").execute();            
                DatabaseConnection dbConnection = new DatabaseConnection(connection);
                operation.execute(dbConnection, dataSet);
            } catch (DatabaseUnitException mapped) {
                throw new SQLException("Mapped DatabaseUnitException: " + mapped.getMessage(),mapped);
            }
            return null;
        }
    
    };
    
}