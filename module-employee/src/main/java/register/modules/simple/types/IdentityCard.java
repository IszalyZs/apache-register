package register.modules.simple.types;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.spec.AbstractSpecification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Property(
        editing = Editing.ENABLED,
        maxLength = IdentityCard.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = IdentityCard.Spec.class)
@Parameter(maxLength = IdentityCard.MAX_LEN,
        optionality = Optionality.MANDATORY,
        mustSatisfy = IdentityCard.Spec.class)
@ParameterLayout(named = "Identity card")
@PropertyLayout(named = "Identity card")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdentityCard {
    int MAX_LEN = 8;

    class Spec extends AbstractSpecification<String> {
        @Override
        public String satisfiesSafely(String identityCard) {
            identityCard = identityCard.toLowerCase();
            boolean matches = identityCard.matches("[0-9]{6}[a-z]{2}");
            return matches ? null : "Identity card contains 6 numbers and 2 alphabetic characters: 123456ab !";
        }
    }

}
