package hw_2023_05_15__2_13;

import hw_2023_05_15__2_13.domain.Employee;
import hw_2023_05_15__2_13.exception.*;
import hw_2023_05_15__2_13.service.EmployeeService;
import hw_2023_05_15__2_13.service.ValidatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());

    public static Stream<Arguments> addWithIncorrectNameTestParams() {
        return Stream.of(
                Arguments.of("Макс1"),
                Arguments.of("Макс!"),
                Arguments.of("<Макс>")
        );
    }

    public static Stream<Arguments> addWithIncorrectSurnameTestParams() {
        return Stream.of(
                Arguments.of("Максимов1"),
                Arguments.of("Максимов!"),
                Arguments.of("<Максимов>")
        );
    }

    @BeforeEach
    public void beforeEach() {
        employeeService.add("Макс", "Максимов", 1, 80000);
        employeeService.add("Олег", "Олегов", 2, 50000);
        employeeService.add("Степан", "Степанов", 1, 150000);
    }

    @AfterEach
    public void afterEach() {
        employeeService.getAll().forEach(employee -> employeeService.remove
                (employee.getName(), employee.getSurname(), employee.getDepartment(), employee.getSalary()));
    }

    @Test
    public void addTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Max", "Maximov", 1, 80000);

        Assertions.assertThat(employeeService.add("Max", "Maximov", 1, 80000))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount + 1);
        Assertions.assertThat(employeeService.find
                ("Max", "Maximov", 1, 80000)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("addWithIncorrectNameTestParams")
    public void addWithIncorrectNameTest(String incorrectName) {
        Assertions.assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() -> employeeService.add(incorrectName, "Maximov", 1, 80000));
    }

    @ParameterizedTest
    @MethodSource("addWithIncorrectSurnameTestParams")
    public void addWithIncorrectSurnameTest(String incorrectSurname) {
        Assertions.assertThatExceptionOfType(IncorrectSurnameException.class)
                .isThrownBy(() -> employeeService.add("Max", incorrectSurname, 1, 80000));
    }

    @Test
    public void addWhenAlreadyAddedTest() {
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.add("Макс", "Максимов", 1, 80000));
    }

    @Test
    public void addWhenStorageIsFullTest() {
        Stream.iterate(1, i -> i + 1)
                .limit(7)
                .map(number -> new Employee(
                                "Макс" + ((char) ('a' + number)),
                                "Максимов" + ((char) ('a' + number)),
                                number,
                                80000 + number
                        )
                )
                .forEach(employee ->
                        employeeService.add(
                                employee.getName(),
                                employee.getSurname(),
                                employee.getDepartment(),
                                employee.getSalary()
                        )
                );

        Assertions.assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add("Макс", "Максимов", 1, 80000));
    }

    @Test
    public void removeTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Макс", "Максимов", 1, 80000);

        Assertions.assertThat(employeeService.remove("Макс", "Максимов", 1, 80000))
                .isEqualTo(expected)
                .isNotIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount - 1);
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find
                        ("Макс", "Максимов", 1, 80000));
    }

    @Test
    public void removeWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find
                        ("Сергей", "Сергеев", 1, 80000));
    }

    @Test
    public void findTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Макс", "Максимов", 1, 80000);

        Assertions.assertThat(employeeService.find("Макс", "Максимов", 1, 80000))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount);
    }

    @Test
    public void findWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find
                        ("Сергей", "Сергеев", 1, 80000));
    }

    @Test
    public void getAllTest() {
        Assertions.assertThat(employeeService.getAll())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Employee("Макс", "Максимов", 1, 80000),
                        new Employee("Олег", "Олегов", 2, 50000),
                        new Employee("Степан", "Степанов", 1, 150000)
                );
    }
}
