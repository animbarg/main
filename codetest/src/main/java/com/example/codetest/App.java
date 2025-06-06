package com.example.codetest;

import com.example.codetest.dao.OrganizationDetails;
import com.example.codetest.dto.OrganizationReport;
import com.example.codetest.report.ReportService;
import com.example.codetest.report.impl.ReportServiceImpl;
import com.example.codetest.util.FileReadUtil;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) {
		  // Default file path
//        String defaultFilePath = "C:\\Users\\Amulyaratna Nimbargi\\eclipse-workspaceTest\\codetest\\resources\\test.csv";
        String path = System.getProperty("user.dir")+"/resources/test.csv";

        // Check if a file path is provided as an argument
        String filePath;
        if (args.length > 0 && args[0] != null && !args[0].isBlank()) {
            filePath = args[0]; // Use the provided path
        } else {
            filePath = path; // Use the default path
        }

        // Use FileUtil to read the file
        FileReadUtil fileUtil = new FileReadUtil();
        OrganizationDetails dataStore = fileUtil.readFile(filePath);

        ReportService reportService = new ReportServiceImpl(dataStore);
        var response = reportService.validate();

        print(response);
	}

	private static void print(OrganizationReport response) {
		// TODO Auto-generated method stub
		
		System.out.println("Manager(s) getting less salary count: " + response.getEmployeeWithLessSalary().size() + ":");
        response.getEmployeeWithLessSalary().forEach((name, amount) ->
        System.out.println(name+" earns less by "+amount)
        );

        System.out.println("Manager(s) getting more salary count: " + response.getEmployeeWithMoreSalary().size() + ":");
        response.getEmployeeWithMoreSalary().forEach((name, amount) ->
                System.out.println(name+" earns more by "+amount)
        );

        System.out.println("Manager(s) having depth more than 4 (count: " + response.getEmployeeWithMoreDepth().size() + ":");
        response.getEmployeeWithMoreDepth().forEach((name, depth) ->
                System.out.println(name+" has a reporting depth of "+depth)
        );
		
	}
}
