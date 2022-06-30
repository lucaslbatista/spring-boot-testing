package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    void setup() {
        employee = Employee.builder()
                .id(1L)
                .fistName("Employee")
                .latsName("Employee")
                .email("employee@gmai.com")
                .build();
    }

    @Test
    @DisplayName("JUnit test for saveEmployee method")
    void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee))
                .willReturn(employee);

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going test

        //using org.junit.jupiter.api.Assertions
//        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            employeeService.saveEmployee(employee);
//        });

        //using assertj
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> {
                    employeeService.saveEmployee(employee);
                });

        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("JUnit test for getAllEmployees method")
    void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        //given - precondition or setup
        Employee employee2 = Employee.builder()
                .id(2L)
                .fistName("Employee2")
                .latsName("Employee2")
                .email("employee2@gmai.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        //when - action or the behaviour that we are going test
        List<Employee> allEmployees = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(allEmployees)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        //given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour that we are going test
        List<Employee> allEmployees = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(allEmployees)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("JUnit test for getEmployeeById method")
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
        //given - precondition or setup
        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going test
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(1L);

        //then - verify the output
        assertThat(optionalEmployee).isPresent();
    }

}