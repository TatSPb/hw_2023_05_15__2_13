package hw_2023_05_15__2_13;

import hw_2023_05_15__2_13.domain.Employee;
import hw_2023_05_15__2_13.exception.DepartmentNotFoundException;
import hw_2023_05_15__2_13.service.DepartmentService;
import hw_2023_05_15__2_13.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    public static Stream<Arguments> maxSalaryTestParams() {
        return Stream.of(
                Arguments.of(1, 131_000),
                Arguments.of(2, 122_000),
                Arguments.of(3, 123_000)
        );
    }

    public static Stream<Arguments> MinSalaryTestParams() {
        return Stream.of(
                Arguments.of(1, 121_000),
                Arguments.of(2, 112_000),
                Arguments.of(3, 123_000)
        );
    }

    public static Stream<Arguments> employeesGroupedByDepartmentTestParams() {
        return Stream.of(
                Arguments.of(
                        1,
                        List.of(
                                new Employee("Антон", "Антонов", 1, 121_000),
                                new Employee("Алексей", "Алексеев", 1, 131_000)
                        )
                ),
                Arguments.of(
                        2,
                        List.of(
                                new Employee("Ян", "Янин", 2, 112_000),
                                new Employee("Глеб", "Глебов", 2, 122_000)
                        )
                ),
                Arguments.of(
                        3,
                        Collections.singletonList(new Employee("Жук", "Жуков", 3, 123_000))
                ),

                Arguments.of(
                        4,
                        Collections.emptyList()
                )
        );
    }

    public static Stream<Arguments> sumSalaryFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1, 252_000),
                Arguments.of(2, 234_000),
                Arguments.of(3, 123_000),
                Arguments.of(4, 0)
        );
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.when(employeeService.getAll()).thenReturn(
                List.of(
                        new Employee("Антон", "Антонов", 1, 121_000),
                        new Employee("Алексей", "Алексеев", 1, 131_000),
                        new Employee("Ян", "Янин", 2, 112_000),
                        new Employee("Глеб", "Глебов", 2, 122_000),
                        // new Employee("Мотыль", "Мотыльков", 3, 113_000),
                        new Employee("Жук", "Жуков", 3, 123_000)
                )
        );

    }
    @ParameterizedTest
    @MethodSource("sumSalaryFromDepartmentTestParams")
    public void sumSalaryFromDepartmentTest(int department, double expected) {
        Assertions.assertThat(departmentService.totalSalaryFromDepartment(department))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("maxSalaryTestParams")
    public void maxSalaryTest(int departmentId, double expected) {
        Assertions.assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(departmentId))
                .isEqualTo(expected);
    }

    @Test
    public void maxSalaryFromDepartmentWhenNotFoundTest(){
        Assertions.assertThatExceptionOfType(DepartmentNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMaxSalaryFromDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("MinSalaryTestParams")
    public void MinSalaryTest(int departmentId, double expected) {
        Assertions.assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId))
                .isEqualTo(expected);
    }
    @Test
    public void minSalaryFromDepartmentWhenNotFoundTest(){
        Assertions.assertThatExceptionOfType(DepartmentNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalaryFromDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("employeesGroupedByDepartmentTestParams")
    public void employeesGroupedByDepartmentTest(int departmentId, List<Employee> expected) {
        Assertions.assertThat(departmentService.findAllEmployeesFromDepartment(departmentId))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void employeesGroupedByDepartmentTest() {
        Map<Integer, List<Employee>> expected = Map.of(
                1,
                List.of(
                        new Employee("Антон", "Антонов", 1, 121_000),
                        new Employee("Алексей", "Алексеев", 1, 131_000)
                ),
                2,
                List.of(
                        new Employee("Ян", "Янин", 2, 112_000),
                        new Employee("Глеб", "Глебов", 2, 122_000)
                ),
                3,
                Collections.singletonList(new Employee("Жук", "Жуков", 3, 123_000))
        );
        Assertions.assertThat(departmentService.findEmployeesByDepartment())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }

}
