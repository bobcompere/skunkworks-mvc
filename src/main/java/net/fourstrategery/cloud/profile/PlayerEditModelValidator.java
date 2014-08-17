package net.fourstrategery.cloud.profile;

import net.fourstrategery.cloud.arch.BaseValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("playerEditModelValidator")
public class PlayerEditModelValidator extends BaseValidator {

	@Override
	public boolean supports(Class<?> c) {
		return c.equals(PlayerEditModel.class);
	}

	@Override
	protected boolean skipAnnotationValidations() {
		return false;
	}

	@Override
	protected void customValidations(Object target, Errors errors) {
		PlayerEditModel pem = (PlayerEditModel) target;
		
		if (!pem.getPasswordChange1().trim().equals(pem.getPasswordChange2())) {
			errors.rejectValue("passwordChange1", "PasswordsMismatch","Passwords Do Not Match");
		}
		
	}

}
