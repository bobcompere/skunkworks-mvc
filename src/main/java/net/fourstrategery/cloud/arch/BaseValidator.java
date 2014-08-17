package net.fourstrategery.cloud.arch;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.Errors;

/**
 * Base validator class which processes annotated validation constraints as well as custom
 * validation logic.
 * 
 * @author jdk
 * 
 */
public abstract class BaseValidator implements org.springframework.validation.Validator, ApplicationContextAware,
		ConstraintValidatorFactory {

	@Autowired
	private Validator validator;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidatorFactory#getInstance(java.lang.Class)
	 * Not sure what this does. It just needs to be here.
	 */
	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		Map<String, T> beansByNames = applicationContext.getBeansOfType(key);
		if (beansByNames.isEmpty()) {
			try {
				return key.newInstance();
			}
			catch (InstantiationException e) {
				throw new RuntimeException("Could not instantiate constraint validator class '" + key.getName() + "'", e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException("Could not instantiate constraint validator class '" + key.getName() + "'", e);
			}
		}
		if (beansByNames.size() > 1) {
			throw new RuntimeException("Only one bean of type '" + key.getName() + "' is allowed in the application context");
		}
		return beansByNames.values().iterator().next();
	}

	@Override
	public void validate(Object target, Errors errors) {

		// only run annotation validation if doAnnotationValidations is True
		if (!skipAnnotationValidations()) {
			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
			for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
				String propertyPath = constraintViolation.getPropertyPath().toString();
				String message = constraintViolation.getMessage();
				errors.rejectValue(propertyPath, "", message);
			}
		}

		// run the custom validations
		customValidations(target, errors);
	}

	/**
	 * Validates a single named property of a given object.
	 * 
	 * @param target
	 *            the object
	 * @param name
	 *            the named property
	 * @param errors
	 *            the errors
	 */
	public void validateProperty(Object target, String name, Errors errors) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validateProperty(target, name);
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			String propertyPath = constraintViolation.getPropertyPath().toString();
			String message = constraintViolation.getMessage();
			errors.rejectValue(propertyPath, "", message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public abstract boolean supports(Class<?> c);

	/**
	 * If True, annotation validations will be skipped (not recommended) but custom validations
	 * will still be run.
	 * If False, annotation validations on the model will be performed.
	 * 
	 * @return
	 */
	protected abstract boolean skipAnnotationValidations();

	/**
	 * Add any custom validations to this method. You will need to cast the target
	 * parameter to the actual model class.
	 * Errors should be added to the errors object so they aggregate with annotation
	 * error messages.
	 * 
	 * @param target
	 * @param errors
	 */
	protected abstract void customValidations(Object target, Errors errors);
}