package register.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import register.modules.simple.entity.employee.Employee;
import register.modules.simple.entity.employee.EmployeeRepository;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        logicalTypeName = "register.HomePageViewModel"
)
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

    public String title() {
        return getEmployees().size() + " objects";
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Inject
    EmployeeRepository employeeRepository;
}
