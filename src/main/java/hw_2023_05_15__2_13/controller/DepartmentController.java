package hw_2023_05_15__2_13.controller;

import hw_2023_05_15__2_13.domain.Employee;
import hw_2023_05_15__2_13.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/{id}/employees")
    public List<Employee> employeesFromDepartment(@PathVariable int id) {
        return departmentService.findAllEmployeesFromDepartment(id);
    }
    @GetMapping("/{id}/salary/sum")
    public double sumSalaryFromDepartment(@PathVariable int id) {
        return departmentService.totalSalaryFromDepartment(id);
    }
    @GetMapping("/{id}/salary/max")
    public double maxSalaryFromDepartment(@PathVariable int id) {
        return departmentService.findEmployeeWithMaxSalaryFromDepartment(id);
    }
    @GetMapping("/{id}/salary/min")
    public double MinSalaryFromDepartment(@PathVariable int id) {
        return departmentService.findEmployeeWithMinSalaryFromDepartment(id);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> employeesGroupedByDepartment() {
        return departmentService.findEmployeesByDepartment();
    }

}
