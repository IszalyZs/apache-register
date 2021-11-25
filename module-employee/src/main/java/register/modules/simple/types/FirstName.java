package register.modules.simple.types;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.spec.AbstractSpecification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Property(
        editing = Editing.ENABLED,
        maxLength = FirstName.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = FirstName.Spec.class)
@Parameter(maxLength = FirstName.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = FirstName.Spec.class)
@ParameterLayout(named = "First Name")
@PropertyLayout(named = "First Name")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstName {
    int MAX_LEN = 40;

    class Spec extends AbstractSpecification<String> {
        @Override
        public String satisfiesSafely(String firstName) {
            firstName = firstName.toLowerCase();
            boolean matches = firstName.matches("[a-z]{1,40}");
            return matches ? null : "FirstName must contain letters from English alphabets!";
        }
    }
}
