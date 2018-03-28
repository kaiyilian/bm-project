package com.bumu.arya.admin.misc.controller.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author majun
 * @date 2017/3/13
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckIdCardNo.class)
@Documented
public @interface IdCardNo {

	String message() default Error.ID_CARD;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	interface Error {
		String ID_CARD = "身份证格式不正确";
	}
}
