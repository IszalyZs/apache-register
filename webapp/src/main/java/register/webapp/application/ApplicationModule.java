package register.webapp.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import register.modules.simple.RegisterModule;

@Configuration
@Import(RegisterModule.class)
@ComponentScan
public class ApplicationModule {

}
