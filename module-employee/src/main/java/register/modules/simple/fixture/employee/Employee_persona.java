package register.modules.simple.fixture.employee;

import lombok.Getter;
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import register.modules.simple.entity.employee.Company;
import register.modules.simple.entity.employee.Employee;
import lombok.AllArgsConstructor;
import register.modules.simple.entity.employee.Employees;

@AllArgsConstructor
public enum Employee_persona
        implements PersonaWithBuilderScript<EmployeeBuilder>, PersonaWithFinder<Employee> {

    JOHN_SMITH("John", "Smith", "1999-11-11", "Manager", "123456AB", Company.ADIDAS),
    WILL_SMITH("Will", "Smith", "1970-12-12", "Director", "123456AC", Company.APPLE),
    BOB_GEORGE("Bob", "George", "1960-12-12", "Sales Manager", "123456AD", Company.IBM),
    KEVIN_COSTNER("Kevin", "Costner", "1979-12-12", "Writer", "123456AF", Company.MICROSOFT);

    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final String birthDate;
    @Getter
    private final String position;
    @Getter
    private final String identityCard;
    @Getter
    private final Company company;

    @Override
    public EmployeeBuilder builder() {
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        employeeBuilder.setFirstName(firstName);
        employeeBuilder.setLastName(lastName);
        employeeBuilder.setBirthDate(birthDate);
        employeeBuilder.setPosition(position);
        employeeBuilder.setIdentityCard(identityCard);
        employeeBuilder.setCompany(company);
        return employeeBuilder;
    }

    @Override
    public Employee findUsing(final ServiceRegistry serviceRegistry) {
        Employees employees = serviceRegistry.lookupService(Employees.class).orElse(null);
        return employees.findByIdentityCardExact(identityCard);
    }

    public static class PersistAll
            extends PersonaEnumPersistAll<Employee_persona, Employee> {
        public PersistAll() {
            super(Employee_persona.class);
        }
    }
}
