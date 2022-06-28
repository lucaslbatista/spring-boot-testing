package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @DisplayName("JUnit test for save employee operation")
    @Test
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .fistName("Employee")
                .latsName("Employee")
                .email("employee@gmai.com")
                .build();

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isPositive();
    }
    @DisplayName("JUnit test for find all employees operation")
    @Test
    void givenEmployeeList_whenFindAll_thenEmployeeList() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .fistName("Employee")
                .latsName("Employee")
                .email("employee@gmai.com")
                .build();

        Employee employee1 = Employee.builder()
                .fistName("Employee1")
                .latsName("Employee1")
                .email("employee1@gmai.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behaviour we're testing
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList)
                .isNotNull()
                .hasSize(2);
    }

    @DisplayName("JUnit test for get employee by id operation")
    @Test
    void givenEmployee_whenSave_thenFindById() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .fistName("Employee")
                .latsName("Employee")
                .email("employee@gmai.com")
                .build();

        employeeRepository.save(employee);

        //when - action or the behaviour we're testing
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(optionalEmployee)
                .containsSame(employee);
    }

    @Test
    @DisplayName("JUnit test for get employee by email operation")
    void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .fistName("Employee")
                .latsName("Employee")
                .email("employee@gmai.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour we're testing
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());

        //then - verify the output
        assertThat(optionalEmployee)
                .containsSame(employee);
    }

    @Test
    void given_when_then() {
        //given - precondition or setup

        //when - action or the behaviour we're testing

        //then - verify the output
    }
}