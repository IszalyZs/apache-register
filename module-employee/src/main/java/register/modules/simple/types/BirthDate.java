package register.modules.simple.types;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.spec.AbstractSpecification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Property(
        editing = Editing.ENABLED,
        maxLength = BirthDate.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = BirthDate.Spec.class)
@Parameter(maxLength = BirthDate.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = BirthDate.Spec.class)
@ParameterLayout(named = "Birth Date")
@PropertyLayout(named = "Birth Date")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
    int MAX_LEN = 10;

    class Spec extends AbstractSpecification<String> {
        @Override
        public String satisfiesSafely(String birthDate) {
            boolean matches = birthDate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
            return matches ? null : "Birth date has a strict format: " + LocalDate.now() + "!";
        }
    }

}