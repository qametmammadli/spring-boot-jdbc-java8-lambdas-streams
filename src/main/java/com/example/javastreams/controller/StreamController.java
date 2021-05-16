package com.example.javastreams.controller;

import com.example.javastreams.domain.Employee;
import com.example.javastreams.service.StreamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class StreamController {

    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @GetMapping("max-salary-in-each-job")
    public void maxSalaryInEachJob(){
        System.out.println("\nMax Employee salary in each job");

        Map<String, List<Employee>> salariesInEachJob = streamService.getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getJobId));

        Map<String, Optional<Employee>> maxSalaryInEachJob = salariesInEachJob.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                        .max(Comparator.comparing(Employee::getSalary))
                ));

        maxSalaryInEachJob.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().get().getSalary()))
                .forEach(employee ->
                        System.out.println(employee.getKey() + " -> "
                                + employee.getValue().get().getFirstName() + " "
                                + employee.getValue().get().getLastName() + " "
                                + employee.getValue().get().getSalary()
                        )
                );
    }

    @GetMapping("sum-of-salary-in-each-job")
    public void sumOfSalary(){
        System.out.println("\nSum of Employee salary in each job in descending order");

        Map<String, BigDecimal> sumOfSalariesInEachJob = streamService.getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getJobId,
                        Collectors.mapping(Employee::getSalary,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))
                );

        sumOfSalariesInEachJob.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(salary -> System.out.println(salary.getKey() + " -> " + salary.getValue()));
    }


    @GetMapping("number-of-employee-in-each-month")
    public void numberOfEmployeeInEachJob(){
        System.out.println("\nNumber of Employee in each job in descending order");

        Map<String, Long> numberOfEmployeeInEachJob = streamService.getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getJobId,
                        Collectors.counting())
                );

        numberOfEmployeeInEachJob.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(job -> System.out.println(job.getKey() + "=" + job.getValue()));
    }


    @GetMapping("joined-number-of-employee-in-each-month")
    public void joinedNumberOfEmployeeInEachMonth(){
        System.out.println("\n Number of Employees in each month in 2000");

        streamService.getEmployees().stream()
                .filter(employee -> employee.getHireDate().split(" ")[2].equals("2000"))
                .collect(Collectors.groupingBy(employee -> employee.getHireDate().split(" ")[0]))
                .forEach((month, employees) -> System.out.println(month + " = " + employees.size()));
    }


    @GetMapping("employees-in-each-department")
    public void employeesInEachDepartment(){
        System.out.println("\n Employees In Each Department");

        Map<String, List<Employee>> employeesInEachDepartment = streamService.getEmployees().stream()
                .collect(Collectors.groupingBy(employee ->
                        streamService.getDepartments().stream()
                                .filter(department -> department.getId() == employee.getDepartmentId())
                                .findFirst().get().getDepartmentName()
                ));

        employeesInEachDepartment.forEach((department_name, employees) -> {
                    System.out.println(department_name + " -> ");
                    employees.forEach(System.out::println);
                    System.out.println();
                }
        );
    }


    @GetMapping("number-of-employees-joined-departments")
    public void employeesJoinedDepartmentInEachYear(){
        System.out.println("\nGet the number of employees joined departments over the years");

        Map<String, Map<String, Long>> employeesJoinedDepartmentInEachYear = streamService.getEmployees().stream()
                .collect(Collectors.groupingBy(employee ->
                                streamService.getDepartments().stream()
                                        .filter(department -> department.getId() == employee.getDepartmentId())
                                        .findFirst().get().getDepartmentName(),
                        Collectors.groupingBy(employee -> employee.getHireDate().split(" ")[2], Collectors.counting())));

        employeesJoinedDepartmentInEachYear.forEach((department_name, numberOfEmployeesInEachYear) ->
                {
                    System.out.println(department_name + ":");
                    numberOfEmployeesInEachYear.forEach((year, numberOfEmployees) ->
                            System.out.println("Year:"+year + " - Number of Employees:" + numberOfEmployees)
                    );
                }
        );
    }

    @GetMapping("managers-and-employees")
    public void managersAndTheirEmployees(){
        System.out.println("\nGet a list of the managers' id, name, surname and their subordinate employees.");

        Map<Long, List<Employee>> managersAndTheirEmployees = streamService.getEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getManagerId));

        managersAndTheirEmployees.forEach((managerId, employees) -> {
            streamService.getEmployees().stream()
                    .filter(employee -> employee.getId() == managerId)
                    .findFirst().ifPresent(manager -> System.out.println(manager.getId() +
                        " " + manager.getFirstName() +
                        " " + manager.getLastName()));

            employees.forEach(employee -> System.out.println("     " + employee.getId() +
                        " " + employee.getFirstName() +
                        " " + employee.getLastName()));
        });
    }
}
