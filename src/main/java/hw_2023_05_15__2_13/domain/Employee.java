package hw_2023_05_15__2_13.domain;

import java.util.Objects;

public class Employee {

    private final String name;

    private final String surname;

    private int department;
    private int salary;

    public Employee(String name, String surname, int department, int salary) {
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee employee = (Employee) obj;
        return name.equals(employee.name) && surname.equals(employee.surname);
    }

    @Override
    public String toString() {
        return surname + " " + name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}
