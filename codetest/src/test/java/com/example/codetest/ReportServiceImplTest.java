package com.example.codetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.codetest.dao.OrganizationDetails;
import com.example.codetest.dto.Employee;
import com.example.codetest.dto.OrganizationReport;
import com.example.codetest.report.impl.ReportServiceImpl;

public class ReportServiceImplTest {
	ReportServiceImpl reportServiceImpl;
	OrganizationDetails details;
	 @BeforeEach
	    void setUp() {
		 details = new OrganizationDetails();
		 reportServiceImpl = new ReportServiceImpl(details);
	    }
	 
	 @Test
	    void testValidate_SalaryBelowMin() {
	        // Arrange
	        Employee manager = new Employee(1, "John", "Doe", 60000L, null);
	        Employee employee = new Employee(2, "Martin", "Chekov", 60000L, 1);

	        details.getEmployeeMap().put(manager.getId(), manager);
	        details.getEmployeeMap().put(employee.getId(), employee);
	        details.getManagerMap().put(manager.getId(), List.of(employee.getId()));

	        // Act
	        OrganizationReport response = reportServiceImpl.validate();

	        // Assert
	        assertFalse(response.getEmployeeWithLessSalary().isEmpty());
	        assertTrue(response.getEmployeeWithLessSalary().containsKey("John Doe"));
	        assertEquals(12000.0, response.getEmployeeWithLessSalary().get("John Doe"));
	    }
	 
	 @Test
	    void testValidate_SalaryAboveMax() {
	        // Arrange
	        Employee manager = new Employee(1, "John", "Doe", 60000L, null);
	        Employee employee = new Employee(2, "Martin", "Chekov", 30000L, 1);

	        details.getEmployeeMap().put(manager.getId(), manager);
	        details.getEmployeeMap().put(employee.getId(), employee);
	        details.getManagerMap().put(manager.getId(), List.of(employee.getId()));

	        // Act
	        OrganizationReport response = reportServiceImpl.validate();

	        // Assert
	        assertFalse(response.getEmployeeWithMoreSalary().isEmpty());
	        assertTrue(response.getEmployeeWithMoreSalary().containsKey("John Doe".trim()));
	        assertEquals(15000.0, response.getEmployeeWithMoreSalary().get("John Doe".trim()));
	    }
	 
	 @Test
	    void testValidate_DepthMoreThanFour() {
	        // Arrange
	        Employee mgr = new Employee(1, "John", "Doe", 200000L, null);
	        Employee mgrSecond = new Employee(2, "Martin", "Chekov", 150000L, 1);
	        Employee subordinate1 = new Employee(3, "Bob", "Ronstad", 120000L, 2);
	        Employee subordinate2 = new Employee(4, "Brett", "Hardleaf", 120000L, 3);
	        Employee subordinate3 = new Employee(5, "William", "Andrews", 90000L, 4);
	        Employee subordinate4 = new Employee(6, "Lydia", "Taylor", 90000L, 5);

	        details.getEmployeeMap().put(mgr.getId(), mgr);
	        details.getEmployeeMap().put(mgrSecond.getId(), mgrSecond);
	        details.getEmployeeMap().put(subordinate1.getId(), subordinate1);
	        details.getEmployeeMap().put(subordinate2.getId(), subordinate2);
	        details.getEmployeeMap().put(subordinate3.getId(), subordinate3);
	        details.getEmployeeMap().put(subordinate4.getId(), subordinate4);

	        details.getManagerMap().put(mgr.getId(), List.of(mgrSecond.getId()));
	        details.getManagerMap().put(mgrSecond.getId(), List.of(subordinate1.getId()));
	        details.getManagerMap().put(subordinate1.getId(), List.of(subordinate2.getId()));
	        details.getManagerMap().put(subordinate2.getId(), List.of(subordinate3.getId()));
	        details.getManagerMap().put(subordinate3.getId(), List.of(subordinate4.getId()));

	        // Act
	        OrganizationReport response = reportServiceImpl.validate();

	        // Assert
	        assertFalse(response.getEmployeeWithMoreDepth().isEmpty());
	        assertTrue(response.getEmployeeWithMoreDepth().containsKey("John Doe"));
	        assertEquals(1, response.getEmployeeWithMoreDepth().get("John Doe"));
	    }

}
