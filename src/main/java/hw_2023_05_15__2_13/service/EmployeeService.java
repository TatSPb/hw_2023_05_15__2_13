package hw_2023_05_15__2_13.service;

import hw_2023_05_15__2_13.domain.Employee;
import hw_2023_05_15__2_13.exception.EmployeeAlreadyAddedException;
import hw_2023_05_15__2_13.exception.EmployeeNotFoundException;
import hw_2023_05_15__2_13.exception.EmployeeStorageIsFullException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    private static final int SIZE = 10;
    private final List<Employee> employees = new ArrayList<>(SIZE);

    private final ValidatorService validatorService; // подключаем ValidatorService

    public EmployeeService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }


    @PostConstruct
    public void init() {

    }

    public Employee add(String name, String surname, int department, int salary) {

        Employee employee = new Employee(
                validatorService.validateName(name),
                validatorService.validateSurname(surname),
                department,
                salary);
        if (employees.size() < SIZE) {
            for (Employee emp : employees) {
                if (emp.equals(employee)) {
                    throw new EmployeeAlreadyAddedException(employee);
                }
            }
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException(employee);
    }

    public Employee find(String name, String surname, int department, int salary) {
        Employee employee = new Employee(name, surname, department, salary);
        if (employees.contains(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException(employee);
    }

    public Employee remove(String name, String surname, int department, int salary) {
        Employee employee = new Employee(name, surname, department, salary);
        if (employees.remove(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException(employee);
    }

    public List<Employee> list(){
        return Collections.unmodifiableList(employees);
    }
    public List<Employee> getAll(){
        return new ArrayList<>(employees);
    }


}


