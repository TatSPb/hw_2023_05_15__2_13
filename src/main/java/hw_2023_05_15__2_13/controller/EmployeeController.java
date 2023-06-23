package hw_2023_05_15__2_13.controller;

import hw_2023_05_15__2_13.domain.Employee;
import hw_2023_05_15__2_13.exception.EmployeeAlreadyAddedException;
import hw_2023_05_15__2_13.exception.EmployeeNotFoundException;
import hw_2023_05_15__2_13.exception.EmployeeStorageIsFullException;
import hw_2023_05_15__2_13.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Employee add(@RequestParam ("name") String name,
                        @RequestParam ("surname")String surname,
                        @RequestParam ("departmentId")int department,
                        @RequestParam ("salary") int salary) {
        return  employeeService.add(name, surname, department, salary);
    }
    @GetMapping("/remove")
    public Employee remove(@RequestParam ("name") String name,
                           @RequestParam ("surname")String surname,
                           @RequestParam ("departmentId")int department,
                           @RequestParam ("salary") int salary) {
        return  employeeService.remove(name, surname, department, salary);
    }
    @GetMapping("/find")
    public Employee find(@RequestParam ("name") String name,
                         @RequestParam ("surname")String surname,
                         @RequestParam ("departmentId")int department,
                         @RequestParam ("salary") int salary) {
        return  employeeService.find(name, surname, department, salary);
    }

    @GetMapping
    public List<Employee> list() {
        return  employeeService.list();
    }
    @ExceptionHandler(EmployeeAlreadyAddedException.class)
    public ResponseEntity<String> EmployeeAlreadyAddedExceptionHandler (EmployeeAlreadyAddedException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Сотрудник " + e.getEmployee() + " уже есть в списке!");
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> EmployeeNotFoundExceptionHandler (EmployeeNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Сотрудник " + e.getEmployee() + " не найден!");
    }

    @ExceptionHandler(EmployeeStorageIsFullException.class)
    public ResponseEntity<String> EmployeeStorageIsFullExceptionHandler (EmployeeStorageIsFullException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Список полон - сотрудника " + e.getEmployee() + " не добавить !");
    }

}
