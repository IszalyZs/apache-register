package register.modules.simple.integtests.tests;

import org.apache.isis.applib.services.wrapper.HiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.isis.applib.services.wrapper.InvalidException;

import register.modules.simple.entity.employee.Company;
import register.modules.simple.entity.employee.Employee;
import register.modules.simple.fixture.employee.Employee_persona;
import register.modules.simple.integtests.SimpleModuleIntegTestAbstract;

import java.time.LocalDate;

@Transactional
public class Employee_IntegTest extends SimpleModuleIntegTestAbstract {

    Employee employee;

    @BeforeEach
    public void setUp() {

        employee = fixtureScripts.runPersona(Employee_persona.WILL_SMITH);
    }


    @Nested
    public static class employee extends Employee_IntegTest {

        @Test
        public void accessible() {

            String identityCard = wrap(employee).getIdentityCard();
            Company company = wrap(employee).getCompany();

            assertThat(identityCard).isEqualTo(employee.getIdentityCard());
            assertThat(company).isEqualTo(employee.getCompany());
        }
    }

    @Nested
    public static class lastName extends Employee_IntegTest {

        @Test
        public void not_accessible() {

            assertThrows(HiddenException.class, () -> {


                wrap(employee).getLastName();
            });
        }
    }

    @Nested
    public static class firstName extends Employee_IntegTest {

        @Test
        public void not_accessible() {

            assertThrows(HiddenException.class, () -> {


                wrap(employee).getFirstName();
            });
        }
    }


    @Nested
    public static class updateAndValidation extends Employee_IntegTest {


        @Test
        public void can_be_updated_directly() {


            wrap(employee).updateName("Kiss", "Peter");
            transactionService.flushTransaction();

            assertThat(wrap(employee).getName()).isEqualTo("Kiss Peter");
        }

        @Test
        public void fails_validation_updateName() {


            InvalidException cause = assertThrows(InvalidException.class, () -> {


                wrap(employee).updateName("Kiss", "Peter2");
            });


            assertThat(cause.getMessage()).contains("LastName must contain letters from English alphabets!");
        }

        @Test
        public void fails_validation_birthDate() {


            InvalidException cause = assertThrows(InvalidException.class, () -> {


                wrap(employee).setBirthDate("200-11-11");
            });


            assertThat(cause.getMessage()).contains("Birth date has a strict format: " + LocalDate.now() + "!");
        }

        @Test
        public void fails_validation_identityCard() {


            InvalidException cause = assertThrows(InvalidException.class, () -> {


                wrap(employee).setIdentityCard("12345ab");
            });


            assertThat(cause.getMessage()).contains("Identity card contains 6 numbers and 2 alphabetic characters: 123456ab !");
        }
    }

}
