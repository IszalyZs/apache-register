package register.modules.simple.entity.employee;

import lombok.RequiredArgsConstructor;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jpa.applib.services.JpaSupportService;
import register.modules.simple.types.*;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "register.Employees"
)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class Employees {

    final RepositoryService repositoryService;
    final JpaSupportService jpaSupportService;
    final EmployeeRepository employeeRepository;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Employee create(
            @FirstName final String firstName,
            @LastName final String lastName,
            @BirthDate final String birthDate,
            @Position final String position,
            @IdentityCard final String identityCard,
            final Company company) {
        return repositoryService.persist(Employee.createEmployee(firstName, lastName, birthDate, position, identityCard, company));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public List<Employee> findByIdentityCard(
            @IdentityCard String identityCard) {
        identityCard = identityCard.toUpperCase();
        return repositoryService.allMatches(
                Query.named(Employee.class, Employee.NAMED_QUERY__FIND_BY_IDENTITY_CARD)
                        .withParameter("identityCard", identityCard));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Employee> findByLastName(
            @LastName final String lastName
    ) {
        return employeeRepository.findByLastNameContaining(lastName);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Employee> findByCompany(
            final Company company
    ) {
        return employeeRepository.findAllByCompany(company);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    @Programmatic
    public Employee findByIdentityCardExact(String identityCard) {
        identityCard = identityCard.toUpperCase();
        return employeeRepository.findByIdentityCard(identityCard);
    }


    @Programmatic
    public void ping() {
        jpaSupportService.getEntityManager(Employee.class)
                .ifSuccess(entityManager -> {
                    final TypedQuery<Employee> q = entityManager.createQuery(
                            "SELECT emp FROM Employee emp ORDER BY emp.lastName",
                            Employee.class)
                            .setMaxResults(1);
                    q.getResultList();
                });
    }
}
