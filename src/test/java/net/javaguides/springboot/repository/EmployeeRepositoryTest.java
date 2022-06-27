package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("JUnit test form save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
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
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }
}