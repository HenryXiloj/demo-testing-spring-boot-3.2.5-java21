package com.henry.demotesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.demotesting.model.Employee;
import com.henry.demotesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc  mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public  void setup(){
        employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
    }

    //JUnit test for createEmployee
    @DisplayName("JUnit test for create Employee")
    @Test
    public  void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given  - precondition or setup
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    //JUnit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public  void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {

        var employee1 = Employee.builder()
                .firstName("Henry1")
                .lastName("x1")
                .email("test1@gmail.com")
                .build();

        //given  - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(employee);
        listOfEmployees.add(employee1);

        given(employeeService.getEmployees()).willReturn(listOfEmployees);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()"
                        , CoreMatchers.is(listOfEmployees.size())));

    }

    //JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method (positive scenario)")
    @Test
    public  void givenEmployeeId_whenGetEmployeeId_thenReturnEmployeeObject() throws Exception {

        long employeeId = 1L;
        //given  - precondition or setup
        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    //JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method (negative scenario)")
    @Test
    public  void givenEmployeeId_whenGetEmployeeId_thenReturnNegativeScenarioEmployeeObject() throws Exception {

        long employeeId = 1L;
        //given  - precondition or setup
        given(employeeService.findById(employeeId)).willReturn(Optional.empty());
        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    //JUnit test for update employee REST API
    @DisplayName("JUnit test for update employee REST API (Positive scenario)")
    @Test
    public  void givenUpdatedEmployee_whenUpdateEmployee_thenReturnEmployeeObject() throws Exception {

        //given  - precondition or setup
        long id = 1L;
        given(employeeService.findById(id)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        employee.setEmail("henry2@test.com");
        employee.setFirstName("test2");
        employee.setLastName("x3");

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee))
                    );

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    //JUnit test for update employee REST API
    @DisplayName("JUnit test for update employee REST API (Negative scenario)")
    @Test
    public  void givenUpdatedEmployee_whenUpdateEmployee_thenReturnNegativeScenarioEmployee() throws Exception {

        //given  - precondition or setup
        long id = 1L;
        given(employeeService.findById(id)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //JUnit test for deleteEmployee
    @DisplayName("JUnit test for deleteEmployee")
    @Test
    public  void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {

        long id = 1L;
        //given  - precondition or setup
        willDoNothing().given(employeeService).deleteEmployee(id);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", id));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }
}
