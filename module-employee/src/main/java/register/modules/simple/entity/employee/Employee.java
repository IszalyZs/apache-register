package register.modules.simple.entity.employee;

import java.time.LocalDate;
import java.util.Comparator;

import javax.inject.Inject;
import javax.persistence.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

import register.modules.simple.types.*;


@javax.persistence.Entity
@javax.persistence.Table(
        schema = "register",
        name = "Employee",
        uniqueConstraints = {
                @javax.persistence.UniqueConstraint(name = "Employee__identityCard__UNQ", columnNames = {"identityCard"})
        }
)
@javax.persistence.NamedQueries({
        @javax.persistence.NamedQuery(
                name = Employee.NAMED_QUERY__FIND_BY_IDENTITY_CARD,
                query = "SELECT emp " +
                        "FROM Employee emp " +
                        "WHERE emp.identityCard LIKE :identityCard"
        )
})
@javax.persistence.EntityListeners(IsisEntityListener.class)
@DomainObject(logicalTypeName = "register.Employee", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Employee implements Comparable<Employee> {

    static final String NAMED_QUERY__FIND_BY_IDENTITY_CARD = "Employee.findByIdentityCard";

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @PropertyLayout(fieldSetId = "metadata", sequence = "1")
    private Long id;

    @FirstName
    @Column(name = "firstName", length = FirstName.MAX_LEN, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    @Property(hidden = Where.EVERYWHERE)
    private String firstName;

    @LastName
    @Column(name = "lastName", length = LastName.MAX_LEN, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    @Property(hidden = Where.EVERYWHERE)
    private String lastName;

    public String title() {
        return getFirstName() + " " + getLastName();
    }

    public String iconName() {
        return "employee";
    }

    @IdentityCard
    @Column(name = "identityCard", length = IdentityCard.MAX_LEN, nullable = false, unique = true)
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "data", sequence = "2")
    private String identityCard;

    @BirthDate
    @Column(name = "birthDate", length = BirthDate.MAX_LEN, nullable = false)
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "data", sequence = "3")
    private String birthDate;


    @Position
    @Column(name = "position", length = Position.MAX_LEN, nullable = true)
    @Getter
    @Setter
    @ToString.Include
    @PropertyLayout(fieldSetId = "data", sequence = "4")
    private String position;


    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    @Column(name = "company", nullable = false)
    @PropertyLayout(fieldSetId = "data", sequence = "5")
    @Property(editing = Editing.ENABLED)
    private Company company;

    @Transient
    @PropertyLayout(fieldSetId = "data", sequence = "1")
    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    @PropertyLayout(fieldSetId = "metadata", sequence = "2")
    @Getter
    @Setter
    @ToString.Include
    private LocalDate createdAt;

    public static Employee createEmployee(String firstName,
                                          String lastName,
                                          String birthDate,
                                          String position,
                                          String identityCard,
                                          Company company) {
        val employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setPosition(position);
        employee.setIdentityCard(identityCard.toUpperCase());
        employee.setCreatedAt(LocalDate.now());
        employee.setCompany(company);
        return employee;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "data", describedAs = "You can update the employees full name")
    public Employee updateName(
            @FirstName final String firstName,
            @LastName final String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        return this;
    }

    public String default0UpdateName() {
        return getFirstName();
    }

    public String default1UpdateName() {
        return getLastName();
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            associateWith = "data", position = ActionLayout.Position.PANEL,
            describedAs = "Deletes this object from the persistent datastore")
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }


    private final static Comparator<Employee> comparator =
            Comparator.comparing(Employee::getFirstName).thenComparing(Employee::getLastName).thenComparing(Employee::getIdentityCard);

    @Override
    public int compareTo(final Employee other) {
        return comparator.compare(this, other);
    }


    @Inject
    @javax.persistence.Transient
    RepositoryService repositoryService;
    @Inject
    @javax.persistence.Transient
    TitleService titleService;
    @Inject
    @javax.persistence.Transient
    MessageService messageService;
}
