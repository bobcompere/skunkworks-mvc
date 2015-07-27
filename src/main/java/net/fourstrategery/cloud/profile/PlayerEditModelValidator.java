package net.fourstrategery.cloud.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import net.fourstrategery.cloud.arch.BaseValidator;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.PlayerRepository;

@Component("playerEditModelValidator")
public class PlayerEditModelValidator extends BaseValidator {
	
	@Autowired
	PlayerRepository playerRepository;

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
		
		PlayerEntity existingPlayer = playerRepository.getPlayerByScreenName(pem.getScreenName());
		if (existingPlayer != null && existingPlayer.getId() != pem.getId()) {
			errors.rejectValue("screenName", "ScreenNameInUse","Screen name is already in use");
		}
		
	}

}
