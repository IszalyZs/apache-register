package register.modules.simple.entity.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jpa.applib.services.JpaSupportService;

@ExtendWith(MockitoExtension.class)
class Employees_Test {

    @Mock
    RepositoryService mockRepositoryService;
    @Mock
    JpaSupportService mockJpaSupportService;
    @Mock
    EmployeeRepository mockEmployeeRepository;

    Employees employees;

    @BeforeEach
    public void setUp() {
        employees = new Employees(mockRepositoryService, mockJpaSupportService, mockEmployeeRepository);
    }

    @Nested
    class create {

        @Test
        void happyCase() {


            final String firstName = "Kovacs";
            final String lastName = "Peter";
            final String position = "Manager";
            final Company company = Company.MICROSOFT;
            final String birthDate = "2000-11-11";
            final String identityCard = "123456AP";
            Employee emp = Employee.createEmployee(firstName, lastName, birthDate, position, identityCard, company);
            Employee response;


            when(mockRepositoryService.persist(
                    argThat((ArgumentMatcher<Employee>) simpleObject -> Objects.equals(emp, emp)))
            ).then((Answer<Employee>) invocation -> invocation.getArgument(0));


            response = employees.create(firstName, lastName, birthDate, position, identityCard, company);


            assertThat(response).isNotNull();
            assertThat(response.getName()).isEqualTo("Kovacs Peter");
            assertThat(response.getCompany()).isEqualTo(Company.MICROSOFT);
            assertThat(response.getPosition()).isEqualTo("Manager");
            assertThat(response.getBirthDate()).isEqualTo("2000-11-11");
            assertThat(response.getIdentityCard()).isEqualTo("123456AP");
        }
    }

    @Nested
    class ListAll {

        @Test
        void happyCase() {


            final List<Employee> all = new ArrayList<>();


            when(mockEmployeeRepository.findAll())
                    .thenReturn(all);


            final List<Employee> list = employees.listAll();
            assertThat(all.size()).isEqualTo(0);
            assertThat(list).isEqualTo(all);
        }
    }
}
