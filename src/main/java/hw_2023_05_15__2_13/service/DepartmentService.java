package hw_2023_05_15__2_13.service;

import hw_2023_05_15__2_13.domain.Employee;
import hw_2023_05_15__2_13.exception.DepartmentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public double totalSalaryFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public double findEmployeeWithMaxSalaryFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .max()
                .orElseThrow(DepartmentNotFoundException::new);
    }

    public double findEmployeeWithMinSalaryFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .min()
                .orElseThrow(DepartmentNotFoundException::new);
    }

    public List<Employee> findAllEmployeesFromDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .toList();
    }

    public Map<Integer, List<Employee>> findEmployeesByDepartment() {
        return employeeService.getAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public void changeDepartment(Employee employee, int newDepartment) {
        employeeService.getAll().stream()
                .filter(value -> Objects.equals(employee, value))
                .findFirst()
                .ifPresent(value -> value.setDepartment(newDepartment));
    }

    public void indexSalariesForDepartment(double index, int department) {
        employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .forEach(employee -> employee.setSalary
                        ((int) (employee.getSalary() + employee.getSalary() * index / 100)));
    }

    public double averageSalaryForDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0);
    }


}


