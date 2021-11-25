package register.modules.simple.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.isis.testing.unittestsupport.applib.matchers.ThrowableMatchers;


import register.modules.simple.entity.employee.Company;
import register.modules.simple.entity.employee.Employee;
import register.modules.simple.entity.employee.Employees;
import register.modules.simple.fixture.employee.Employee_persona;
import register.modules.simple.integtests.SimpleModuleIntegTestAbstract;

@Transactional
public class Employees_IntegTest extends SimpleModuleIntegTestAbstract {

    @Inject
    Employees menu;

    @Nested
    public static class listAll extends Employees_IntegTest {

        @Test
        public void happyCase() {

            fixtureScripts.run(new Employee_persona.PersistAll());
            transactionService.flushTransaction();

            final List<Employee> all = wrap(menu).listAll();

            assertThat(all).hasSize(Employee_persona.values().length);
        }

        @Test
        public void whenNone() {


            final List<Employee> all = wrap(menu).listAll();

            assertThat(all).hasSize(0);
        }

    }

    @Nested
    public static class create extends Employees_IntegTest {

        @Test
        public void happyCase() {

            wrap(menu).create("Kiss","Peter","2000-11-11","Manager","123456AB", Company.APPLE);

            final List<Employee> all = wrap(menu).listAll();
            assertThat(all).hasSize(1);
        }

        @Test
        public void whenAlreadyExists() {

            fixtureScripts.runPersona(Employee_persona.KEVIN_COSTNER);
            transactionService.flushTransaction();

            Throwable cause = assertThrows(Throwable.class, () -> {


                wrap(menu).create("Kevin", "Costner", "1979-12-12", "Writer", "123456AF",Company.MICROSOFT);
                transactionService.flushTransaction();

            });

            MatcherAssert.assertThat(cause,
                    ThrowableMatchers.causedBy(DuplicateKeyException.class));

        }

    }

}
