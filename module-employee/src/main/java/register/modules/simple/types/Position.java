package register.modules.simple.types;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.spec.AbstractSpecification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Property(
        editing = Editing.ENABLED,
        maxLength = Position.MAX_LEN,
        optionality = Optionality.OPTIONAL,
        mustSatisfy = Position.Spec.class)
@Parameter(maxLength = Position.MAX_LEN,
        optionality = Optionality.OPTIONAL,
        mustSatisfy = Position.Spec.class)
@ParameterLayout(named = "Position")
@PropertyLayout(named = "Position")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Position {
    int MAX_LEN = 40;

    class Spec extends AbstractSpecification<String> {
        @Override
        public String satisfiesSafely(String position) {
            position = position.toLowerCase();
            boolean matches = position.matches("[a-z\\s]{1,40}");
            return matches ? null : "Position must contain letters from English alphabets!";
        }
    }
}