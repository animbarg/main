package com.example.codetest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.codetest.dto.Employee;

import lombok.Data;

@Data
public class OrganizationDetails {
	 private Map<Integer,Employee> employeeMap = new HashMap<>();
	    private Map<Integer, List<Integer>> managerMap = new HashMap<>();
}
