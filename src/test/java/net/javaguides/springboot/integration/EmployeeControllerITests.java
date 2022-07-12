package net.javaguides.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("JUnit integration test for create employee method")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmail.com")
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));

    }

    // JUnit test for Get All employees REST API
    @Test
    @DisplayName("JUnit integration test for get all employees REST API")
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = List.of(
                Employee.builder().firstName("Employee").lastName("Employee").email("employee@gmail.com").build(),
                Employee.builder().firstName("Employee2").lastName("Employee2").email("employee2@gmail.com").build()
        );
        employeeRepository.saveAll(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    // positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    @DisplayName("JUnit integration test for GET employee by id REST API - positive scenario")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    @DisplayName("JUnit integration test for GET employee by id REST API - negative scenario")
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // JUnit test for update employee REST API - positive scenario
    @Test
    @DisplayName("JUnit integration test for update employee REST API - positive scenario")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("UpdatedEmployee")
                .lastName("UpdatedEmployee")
                .email("employee@gmail.com")
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    // JUnit test for update employee REST API - negative scenario
    @Test
    @DisplayName("JUnit integration test for update employee REST API - negative scenario")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("UpdatedEmployee")
                .lastName("UpdatedEmployee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("UpdatedEmployee")
                .lastName("UpdatedEmployee")
                .email("employee@gmail.com")
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // JUnit test for delete employee REST API
    @Test
    @DisplayName("JUnit integration test for delete employee REST API")
    public void givenEmployeeId_whenDeleteEmployee_thenReturn204() throws Exception {
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

//    @Test
//    @DisplayName("JUnit integration test for delete employee REST API")
//    public void givenEmployeeId_whenDeleteEmployee_thenReturn204() throws Exception {
//        // given - precondition or setup
//        Employee savedEmployee = Employee.builder()
//                .firstName("Employee")
//                .lastName("Employee")
//                .email("employee@gmail.com")
//                .build();
//        employeeRepository.save(savedEmployee);
//
//        // when -  action or the behaviour that we are going test
//        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));
//
//        // then - verify the output
//        response.andExpect(status().isNoContent())
//                .andDo(print());
//    }

}
