package com.henry.demotesting.service;


import com.henry.demotesting.exception.ResourceNotFoundException;
import com.henry.demotesting.model.Employee;
import com.henry.demotesting.repository.EmployeeRepository;
import com.henry.demotesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static  org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public  void setup(){
       /***
        * employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
        **/
        employee = Employee.builder()
                .id(1L)
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
    }

    //JUnit test for savedEmployee method
    @DisplayName("JUnit test for savedEmployee method")
    @Test
    public  void givenEmployeeObject_whenSavedEmployee_thenReturnEmployeeObject(){

        //given  - precondition or setup
       given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

      given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //JUnit test for savedEmployee method which throws exception
    @DisplayName("JUnit test for savedEmployee method which throws exception")
    @Test
    public  void givenExistingEmail_whenSavedEmployee_thenThrowsException(){

        //given  - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public  void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){

        //given  - precondition or setup

        var employee1 = Employee.builder()
                .id(2L)
                .firstName("Henry1")
                .lastName("x1")
                .email("test1@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when - action or the behaviour that we are going test
       var employeeList = employeeService.getEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public  void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){

        //given  - precondition or setup
        var employee1 = Employee.builder()
                .id(2L)
                .firstName("Henry1")
                .lastName("x1")
                .email("test1@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour that we are going test
        var employeeList = employeeService.getEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    //JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public  void givenEmployeeId_whenGetEmployeeId_thenEmployeeObject(){

        //given  - precondition or setup
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going test
        var employeeObject = employeeService.findById(employee.getId());
        // then - verify the output
        assertThat(employeeObject).isNotEmpty();
    }

    //JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public  void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee(){

        //given  - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("henry2@test.com");
        employee.setFirstName("test2");
        employee.setLastName("x3");
       //when - action or the behaviour that we are going test
        var updateEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updateEmployee.getEmail()).isEqualTo("henry2@test.com");
        assertThat(updateEmployee.getFirstName()).isEqualTo("test2");
        assertThat(updateEmployee.getLastName()).isEqualTo("x3");
    }

   //JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public  void givenEmployeeObject_whenDeleteEmployee_thenReturnDeleteObject(){
        long employeeId = 1L;
        //given  - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when - action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
