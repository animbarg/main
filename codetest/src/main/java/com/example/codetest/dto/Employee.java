package com.example.codetest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Employee {
	private Integer id;
    private String firstName;
    private String lastName;
    private Long salary;
    private Integer managerId;
}
