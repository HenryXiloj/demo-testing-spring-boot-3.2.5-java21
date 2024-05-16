package com.henry.demotesting.repository;


import com.henry.demotesting.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public  void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();

        //when - action or the behaviour that we are going test
        var savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

     //JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
     @Test
     public  void givenEmployeeList_whenFindAll_thenEmployeeList(){

         //given  - precondition or setup
         var employee = Employee.builder()
                 .firstName("Henry")
                 .lastName("x")
                 .email("test@gmail.com")
                 .build();

         var employee1 = Employee.builder()
                 .firstName("Henry")
                 .lastName("x")
                 .email("test@gmail.com")
                 .build();

         employeeRepository.save(employee);
         employeeRepository.save(employee1);

         //when - action or the behaviour that we are going test
         var employeeList = employeeRepository.findAll();

         // then - verify the output
         assertThat(employeeList).isNotNull();
         assertThat(employeeList.size()).isEqualTo(2);

     }

     //JUnit test for get employee id operation
    @DisplayName("JUnit test for get employee id operation")
    @Test
    public  void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){

         //given  - precondition or setup
         var employee = Employee.builder()
                 .firstName("Henry")
                 .lastName("x")
                 .email("test@gmail.com")
                 .build();

         employeeRepository.save(employee);

         //when - action or the behaviour that we are going test
          var employeeDB = employeeRepository.findById(employee.getId()).get();

         // then - verify the output
         assertThat(employeeDB).isNotNull();
     }

     //JUnit test for get employee email operation
    @DisplayName("JUnit test for get employee email operation")
     @Test
     public  void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject(){

         //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
        employeeRepository.save(employee);

         //when - action or the behaviour that we are going test
        var employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

         // then - verify the output
        assertThat(employeeDB).isNotNull();
     }

    //JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public  void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee(){

        //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        var savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("henry@gmail.com");
        savedEmployee.setFirstName("henry2");

        var updateEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updateEmployee.getEmail()).isEqualTo("henry@gmail.com");
        assertThat(updateEmployee.getFirstName()).isEqualTo("henry2");
    }

    //JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public  void givenEmployeeObject_whenDelete_thenRemoveEmployee(){

        //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();

    }

    //JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public  void givenFirstNameAndLastName_whenFindByJPQL_thenEmployeeObject(){

        //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
        employeeRepository.save(employee);
        var firstName = "Henry";
        var lastName = "x";

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //JUnit test for custom query using JPQL with nameParams
    @DisplayName("JUnit test for custom query using JPQL with nameParams")
    @Test
    public  void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenEmployeeObject(){

        //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
        employeeRepository.save(employee);
        var firstName = "Henry";
        var lastName = "x";

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNameParams(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for using Nativa SQL with index params
    @DisplayName("JUnit test for using Nativa SQL with index params")
    @Test
    public  void givenFirstNameAndLastName_whenFindByNativeSQL_thenEmployeeObject(){

        //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
        employeeRepository.save(employee);
        var firstName = "Henry";
        var lastName = "x";

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for using Nativa SQL with named params
    @DisplayName("JUnit test for using Nativa SQL with named params")
    @Test
    public  void givenFirstNameAndLastName_whenFindByNativeSQLWithParams_thenEmployeeObject(){

        //given  - precondition or setup
        var employee = Employee.builder()
                .firstName("Henry")
                .lastName("x")
                .email("test@gmail.com")
                .build();
        employeeRepository.save(employee);
        var firstName = "Henry";
        var lastName = "x";

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLWithNameParams(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }
}
