package com.example.codetest.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
@Data
public class OrganizationReport {
	private Map<String, Double> employeeWithLessSalary = new HashMap<>();
    private Map<String, Double> employeeWithMoreSalary = new HashMap<>();
    private Map<String, Integer> employeeWithMoreDepth = new HashMap<>();
}
