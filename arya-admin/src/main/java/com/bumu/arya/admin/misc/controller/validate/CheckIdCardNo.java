package com.bumu.arya.admin.misc.controller.validate;

import com.bumu.arya.IdcardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author majun
 * @date 2017/3/13
 */
public class CheckIdCardNo implements ConstraintValidator<IdCardNo, String> {
	private static Logger logger = LoggerFactory.getLogger(CheckIdCardNo.class);

	@Override
	public void initialize(IdCardNo constraintAnnotation) {
		logger.info("CheckIdCardNo initialize...");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		logger.info("验证身份证号码");
		return IdcardValidator.isValidatedAllIdcard(value);
	}
}
