package net.fourstrategery.cloud.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;


public class FsPasswordEncoder implements PasswordEncoder {

	private static int MINPASSWORDLENGTH = 8;


	@Override
	public String encode(CharSequence rawPass) {
		return BCrypt.hashpw(rawPass.toString(), BCrypt.gensalt());
	}

	@Override
	public boolean matches(CharSequence rawPass, String encPass) {
		// check if the password entered is correct
		boolean returnVal = BCrypt.checkpw(rawPass.toString(), encPass);
		return returnVal;
	}

	
}
