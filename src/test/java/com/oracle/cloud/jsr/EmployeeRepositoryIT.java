package com.oracle.cloud.jsr;

import com.oracle.cloud.jsr.jpa.EmployeeRepository;
import com.oracle.cloud.jsr.jpa.JPAFacade;
import com.oracle.cloud.jsr.jpa.entities.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author agupgupt
 */
public class EmployeeRepositoryIT {

    public EmployeeRepositoryIT() {
    }
    static Map<String, String> props = new HashMap<>();
    final static String PU_NAME = "dbcs-pu";

    @BeforeClass
    public static void setUpClass() {

        props.put("javax.persistence.jdbc.url", System.getenv().getOrDefault("DBCS_JDBC_URL", "jdbc:oracle:thin:@129.144.18.61:1521/PDB1.paasdemo015.oraclecloud.internal"));
        props.put("javax.persistence.jdbc.user", System.getenv().getOrDefault("DBCS_USER", "abhi"));
        props.put("javax.persistence.jdbc.password", System.getenv().getOrDefault("DBCS_PASSWORD", "Password_123"));
        
        JPAFacade.bootstrapEMF(PU_NAME, props);

    }

    @AfterClass
    public static void tearDownClass() {
        props.clear();
        props = null;

        JPAFacade.closeEMF();
    }

    EmployeeRepository cut;

    @Before
    public void setUp() {
        cut = new EmployeeRepository();
    }

    @After
    public void tearDown() {
        //nothing to do
    }

    @Test
    public void getSingleEmployeeTest() {
        String id = "007";
        String name = "Abhishek Gupta";
        
        Employee emp = cut.get(id);
        assertNotNull("Employee was null!", emp);
        assertEquals("Wrong employee id", emp.getEmpId(), Integer.valueOf(id));
        assertEquals("Wrong employee name", emp.getFullName(), name);
    }
    
    @Test
    public void getAllEmployeesTest() {
        List<Employee> emps = cut.all();
        assertNotNull("Employee list was null!", emps);
        assertEquals("2 employees were not found", emps.size(), 2);
    }

}
