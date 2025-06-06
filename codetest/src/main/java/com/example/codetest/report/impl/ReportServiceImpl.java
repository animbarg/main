package com.example.codetest.report.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.codetest.dao.OrganizationDetails;
import com.example.codetest.dto.Employee;
import com.example.codetest.dto.OrganizationReport;
import com.example.codetest.report.ReportService;

public class ReportServiceImpl implements ReportService {
	OrganizationDetails organizationDetails;
	
	public ReportServiceImpl(OrganizationDetails organizationDetails) {
        this.organizationDetails = organizationDetails;
    }

	@Override
	public OrganizationReport validate() {
		// TODO Auto-generated method stub

		OrganizationReport response = new OrganizationReport();
        List<Employee> employees = new ArrayList<>(organizationDetails.getEmployeeMap().values());

        // Evaluating salary of manager
        employees.forEach(emp -> {
            double avgSalary = calculateAverageSalary(emp.getId());
            double minSalary = avgSalary * 1.2; // 20% above avg
            double maxSalary = avgSalary * 1.5; // 50% above avg

            
            if (avgSalary > 0) {
                if (emp.getSalary() < minSalary) {
                    response.getEmployeeWithLessSalary().put(
                            emp.getFirstName()+" "+emp.getLastName(), (minSalary - emp.getSalary())
                    );
                } else if (emp.getSalary() > maxSalary) {
                    response.getEmployeeWithMoreSalary().put(
                    		emp.getFirstName()+" "+emp.getLastName(), (emp.getSalary() - maxSalary)
                    );
                }
            }
        });

        // Check for reporting lines longer than 4 levels
        employees.forEach(emp -> {
            int currentHeight = getHeight(emp.getId());
            if (currentHeight > 4) {
                response.getEmployeeWithMoreDepth().put(
                		emp.getFirstName()+" "+emp.getLastName(), (currentHeight - 4)
                );
            }
        });
        return response;
    
	}
	
	
	 /**
     *  average salary of all subordinates under a  manager.
     */
	private double calculateAverageSalary(Integer managerId) {
        List<Integer> subordinates = getAllSubordinates(managerId);
        if (subordinates.isEmpty()) {
            return 0;
        }
        double totalSalary = subordinates.stream()
                .mapToDouble(subId -> organizationDetails.getEmployeeMap().get(subId).getSalary())
                .sum();
        return totalSalary / subordinates.size();
    }
	
	/**
     * Recursively retrieves all subordinates under a manager.
     */
    private List<Integer> getAllSubordinates(Integer managerId) {
        List<Integer> allSubordinates = new ArrayList<>();
        if (organizationDetails.getManagerMap().containsKey(managerId)) {
        	organizationDetails.getManagerMap().get(managerId).forEach(subId -> {
                allSubordinates.add(subId);
                allSubordinates.addAll(getAllSubordinates(subId)); // Recursion for deeper levels
            });
        }
        return allSubordinates;
    }
	
	/**
     * Calculates the height of the reporting structure for a given manager.
     */
    private int getHeight(Integer managerId) {
        if (managerId == null) {
            return 0;
        }
        List<Integer> subordinates = organizationDetails.getManagerMap().get(managerId);
        if (subordinates == null || subordinates.isEmpty()) {
            return 0;
        }
        return subordinates.stream()
                .mapToInt(this::getHeight)
                .max()
                .orElse(0) + 1;
    }

}
