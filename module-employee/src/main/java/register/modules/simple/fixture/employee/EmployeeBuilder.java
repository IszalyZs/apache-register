package register.modules.simple.fixture.employee;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;

import register.modules.simple.entity.employee.Company;
import register.modules.simple.entity.employee.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import register.modules.simple.entity.employee.Employees;

@Accessors(chain = true)
public class EmployeeBuilder extends BuilderScriptWithResult<Employee> {

    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String birthDate;
    @Getter
    @Setter
    private String position;
    @Getter
    @Setter
    private String identityCard;
    @Getter
    @Setter
    private Company company;


    @Override
    protected Employee buildResult(final ExecutionContext ec) {

        checkParam("firstName", ec, String.class);
        checkParam("lastName", ec, String.class);
        checkParam("birthDate", ec, String.class);
        checkParam("position", ec, String.class);
        checkParam("identityCard", ec, String.class);
        checkParam("company", ec, Enum.class);

        Employee employee = employees.findByIdentityCardExact(identityCard);
        if (employee == null) {
            employee = wrap(employees).create(firstName, lastName, birthDate, position, identityCard, company);
        }
        return this.object = employee;
    }

    @Inject
    Employees employees;

}
