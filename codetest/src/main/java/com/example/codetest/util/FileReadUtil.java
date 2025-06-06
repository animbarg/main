package com.example.codetest.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.codetest.dao.OrganizationDetails;
import com.example.codetest.dto.Employee;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class FileReadUtil {
	public OrganizationDetails readFile(String filePath) {
		OrganizationDetails organizationDetails = new OrganizationDetails();
		final Map<Integer, List<Integer>> managerMap = new HashMap<>();
		final Map<Integer, Employee> empoyeeMap = new HashMap<>();

		if (filePath == null || filePath.isBlank()) {
			throw new IllegalArgumentException("Invalid file");
		}
		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1). // Skiping firstline
																									// as it is header
				build();) {

			// Read all lines and process them
			csvReader.readAll().stream().map(this::parseEmployee).forEach(employee -> {
				empoyeeMap.put(employee.getId(), employee);
				if (employee.getManagerId() != null) {
					managerMap.putIfAbsent(employee.getManagerId(), new ArrayList<>());
					managerMap.get(employee.getManagerId()).add(employee.getId());
				}
			});

			organizationDetails.setManagerMap(managerMap);
			organizationDetails.setEmployeeMap(empoyeeMap);

		} catch (IOException e) {
			throw new RuntimeException("Error reading file: " + e.getMessage(), e);
		}
		return organizationDetails;
	}

	private Employee parseEmployee(String[] line) {

		int id = Integer.parseInt(line[0]);
		String firstName = line[1];
		String lastName = line[2];
		long salary = Long.parseLong(line[3]);
		Integer managerId = line.length > 4 && !line[4].isEmpty() ? Integer.parseInt(line[4]) : null;

		return Employee.builder().id(id).firstName(firstName).lastName(lastName).salary(salary).managerId(managerId)
				.build();
	}
}
