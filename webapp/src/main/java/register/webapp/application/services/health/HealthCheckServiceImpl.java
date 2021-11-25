package register.webapp.application.services.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import org.apache.isis.applib.services.health.Health;
import org.apache.isis.applib.services.health.HealthCheckService;
import register.modules.simple.entity.employee.Employees;

@Service
@Named("register.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

    private final Employees employees;

    @Inject
    public HealthCheckServiceImpl(Employees employees) {
        this.employees = employees;
    }

    @Override
    public Health check() {
        try {
            employees.ping();
            return Health.ok();
        } catch (Exception ex) {
            return Health.error(ex);
        }
    }
}
