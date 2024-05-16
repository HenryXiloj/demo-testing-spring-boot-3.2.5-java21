package com.henry.demotesting.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.demotesting.model.Employee;
import com.henry.demotesting.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

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

    private Employee employee;

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();

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
        employeeRepository.saveAll(listOfEmployees);
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

        //given  - precondition or setup
        var employee1 = employeeRepository.save(employee);
        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee1.getId()));

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
        employeeRepository.save(employee);
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
        Employee employee1 = employeeRepository.save(employee);
        employee1.setEmail("henry2@test.com");
        employee1.setFirstName("test2");
        employee1.setLastName("x3");
        employeeRepository.save(employee1);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employee1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1))
        );

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee1.getEmail())));
    }

    //JUnit test for update employee REST API
    @DisplayName("JUnit test for update employee REST API (Negative scenario)")
    @Test
    public  void givenUpdatedEmployee_whenUpdateEmployee_thenReturnNegativeScenarioEmployee() throws Exception {

        //given  - precondition or setup
        long id = 1L;
        employeeRepository.save(employee);

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

        //given  - precondition or setup
        var employee1 = employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee1.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }
}
