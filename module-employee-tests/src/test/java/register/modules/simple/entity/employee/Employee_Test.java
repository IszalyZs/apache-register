package register.modules.simple.entity.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

@ExtendWith(MockitoExtension.class)
class Employee_Test {

    @Mock
    TitleService mockTitleService;
    @Mock
    MessageService mockMessageService;
    @Mock
    RepositoryService mockRepositoryService;

    Employee employee;

    @BeforeEach
    public void setUp() throws Exception {
        employee = Employee.createEmployee("Kiss", "Peter", "2000-11-11", "Manager", "111111as", Company.NIKE);
        employee.titleService = mockTitleService;
        employee.messageService = mockMessageService;
        employee.repositoryService = mockRepositoryService;
    }

    @Nested
    public class updateName {

        @Test
        void happy_case() {
            assertThat(employee.getName()).isEqualTo("Kiss Peter");
            assertThat(employee.getCompany()).isEqualTo(Company.NIKE);
            assertThat(employee.getBirthDate()).isEqualTo("2000-11-11");
            assertThat(employee.getPosition()).isEqualTo("Manager");
            assertThat(employee.getIdentityCard()).isEqualTo("111111AS");

            employee.updateName("Kovacs", "Geza");
            assertThat(employee.getName()).isEqualTo("Kovacs Geza");

        }

    }

    @Nested
    class delete {

        @Test
        void happy_case() throws Exception {

            assertThat(employee).isNotNull();
            when(mockTitleService.titleOf(employee)).thenReturn("Kiss Peter");
            employee.delete();

            verify(mockMessageService).informUser("'Kiss Peter' deleted");
            verify(mockRepositoryService).removeAndFlush(employee);
        }
    }
}