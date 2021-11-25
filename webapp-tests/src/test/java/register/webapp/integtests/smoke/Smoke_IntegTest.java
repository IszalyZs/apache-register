package register.webapp.integtests.smoke;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.services.wrapper.InvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import org.apache.isis.applib.services.xactn.TransactionService;

import static org.assertj.core.api.Assertions.assertThat;

import register.modules.simple.entity.employee.Company;
import register.modules.simple.entity.employee.Employees;
import register.webapp.integtests.WebAppIntegTestAbstract;
import register.modules.simple.entity.employee.Employee;

@Transactional
class Smoke_IntegTest extends WebAppIntegTestAbstract {

    @Inject
    Employees menu;
    @Inject TransactionService transactionService;

    @Test
    void happy_case() {

        List<Employee> all = wrap(menu).listAll();
        assertThat(all).isEmpty();

        Employee employee1 = wrap(menu).create("Kiss", "Peter", "2000-11-11", "Manager", "123456AR", Company.APPLE);
        transactionService.flushTransaction();

        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);
        assertThat(all).contains(employee1);


        Employee employee2 = wrap(menu).create("Nagy", "Adam", "2000-12-12", "Manager", "123456AV", Company.APPLE);
        transactionService.flushTransaction();

        all = wrap(menu).listAll();
        assertThat(all).hasSize(2);
        assertThat(all).contains(employee1,employee2);


        wrap(employee1).updateName("Kiss","Imre");
        transactionService.flushTransaction();

        assertThat(wrap(employee1).getName()).isEqualTo("Kiss Imre");


        Assertions.assertThrows(InvalidException.class, () -> {
            wrap(employee1).updateName("Kiss","Imre2");
            transactionService.flushTransaction();
        }, "LastName must contain letters from English alphabets!");


        assertThat(wrap(employee1).getPosition()).isEqualTo("Manager");


        wrap(employee1).delete();
        transactionService.flushTransaction();

        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);
    }

}

