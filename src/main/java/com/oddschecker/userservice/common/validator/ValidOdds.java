package com.oddschecker.userservice.common.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.oddschecker.userservice.odds.service.OddsCheckService;
import lombok.RequiredArgsConstructor;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidOdds.Validator.class)
public @interface ValidOdds {
    String message() default "Invalid format of Odds";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class Validator implements ConstraintValidator<ValidOdds, String> {

        private final OddsCheckService oddsCheckService;
        @Override
        public boolean isValid(String odds, ConstraintValidatorContext context) {
            return oddsCheckService.isOddsValid(odds);
        }
    }
}
