package net.javaguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("JUnit test for create employee method")
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmai.com")
                .build();

        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee))
        );

        //then - verify the output
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail()))
                );
    }

    @Test
    @DisplayName("JUnit test for get ALL employee REST API")
    void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmai.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Employee2")
                .lastName("Employee2")
                .email("employee2@gmai.com")
                .build();

        List<Employee> employeeList = List.of(employee, employee2);

        given(employeeService.getAllEmployees()).willReturn(employeeList);


        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(employeeList.size())));
    }

    @Test
    @DisplayName("JUnit test for get by id employee REST API - POSITIVE SCENARIO")
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmai.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("JUnit test for get by id employee REST API - NEGATIVE SCENARIO")
    void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        given(employeeService.getEmployeeById(anyLong())).willReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for update employee REST API - POSITIVE SCENARIO")
    void givenUpdateEmployee_whenUpdateEmployee_thenReturnUpdate() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Employee")
                .lastName("Employee")
                .email("employee@gmai.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Employee2")
                .lastName("Employee2")
                .email("employee2@gmai.com")
                .build();

        //when - action or the behaviour that we are going test
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions reponse = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        //then - verify the output


        reponse.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    @Test
    @DisplayName("JUnit test for update employee REST API - NEGATIVE SCENARIO")
    void givenInvalidEmployeeId_whenUpdateEmployee_thenReturnEmpty() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        Employee updatedEmployee = Employee.builder()
                .firstName("Employee2")
                .lastName("Employee2")
                .email("employee2@gmai.com")
                .build();

        //when - action or the behaviour that we are going test
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        ResultActions reponse = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output


        reponse.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for delete employee REST API - positive scenario")
    void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

//    @Test
//    @DisplayName("JUnit test for delete employee REST API - negative scenario")
//    void givenEmployeeId_whenDeleteEmployee_thenThrowsException() throws Exception {
//        //given - precondition or setup
//        long employeeId = 1L;
//        willDoNothing().given(employeeService).deleteEmployee(employeeId);
//
//        //when - action or the behaviour that we are going test
//        //ResultActions response = ;
//
//        //then - verify the output
//        mockMvc.perform(delete("/api/employees/{id}", employeeId))
//                .andExpect(status().isNoContent())
//                .andExpect(result -> assertThat(result.getResolvedException() instanceof ResourceNotFoundException))
//                .andDo(print());
//
//    }

}