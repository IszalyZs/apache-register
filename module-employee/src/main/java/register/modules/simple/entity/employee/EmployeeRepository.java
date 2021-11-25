package register.modules.simple.entity.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByIdentityCard(String identityCard);

    List<Employee> findByLastNameContaining(String name);

    List<Employee> findAllByCompany(Company company);
}
