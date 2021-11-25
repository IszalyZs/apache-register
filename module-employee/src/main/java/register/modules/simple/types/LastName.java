package register.modules.simple.types;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.spec.AbstractSpecification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Property(
        editing = Editing.ENABLED,
        maxLength = LastName.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = LastName.Spec.class)
@Parameter(maxLength = LastName.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = LastName.Spec.class)
@ParameterLayout(named = "Last Name")
@PropertyLayout(named = "Last Name")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastName {
    int MAX_LEN = 40;

    class Spec extends AbstractSpecification<String> {
        @Override
        public String satisfiesSafely(String lastName) {
            lastName = lastName.toLowerCase();
            boolean matches = lastName.matches("[a-z]{1,40}");
            return matches ? null : "LastName must contain letters from English alphabets!";
        }
    }

}

