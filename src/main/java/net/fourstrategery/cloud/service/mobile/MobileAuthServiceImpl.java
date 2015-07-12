package net.fourstrategery.cloud.service.mobile;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.fourstrategery.cloud.entity.PlayerAuthEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.PlayerAuthRepository;
import net.fourstrategery.cloud.repository.PlayerRepository;
import net.fourstrategery.cloud.security.FsPasswordEncoder;

@Service
public class MobileAuthServiceImpl implements MobileAuthService {

	@Autowired
	FsPasswordEncoder passwordEncoder;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerAuthRepository playerAuthRepository;
	
	private SecureRandom random = new SecureRandom();
	
	@Override
	public AuthToken authenticate(String userName, String password) {
		AuthToken retval = new AuthToken();
		
		PlayerEntity player = playerRepository.getPlayerByScreenName(userName);
		if (player != null) {
			if (passwordEncoder.matches(password, player.getPassword())) {
				retval.setPlayer_id(player.getId());
				retval.setAuthenticated(true);
				retval.setToken(new BigInteger(130, random).toString(32));
				
				PlayerAuthEntity pae = new PlayerAuthEntity();
				pae.setPlayer(player);
				pae.setAuthenticationToken(retval.getToken());
				
				playerAuthRepository.save(pae);
			}
		}
		
		
		return retval;
	}

}
